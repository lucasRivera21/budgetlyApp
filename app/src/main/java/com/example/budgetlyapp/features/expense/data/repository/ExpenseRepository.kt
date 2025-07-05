package com.example.budgetlyapp.features.expense.data.repository

import android.util.Log
import com.example.budgetlyapp.ExpenseCollection
import com.example.budgetlyapp.ExpenseGroupCollection
import com.example.budgetlyapp.UsersCollection
import com.example.budgetlyapp.common.domain.models.ExpenseModelFromDb
import com.example.budgetlyapp.common.domain.models.ExpensesGroupModel
import com.example.budgetlyapp.common.domain.models.TagModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "ExpenseRepository"

interface ExpenseTask {
    suspend fun getExpenseGroupList(): Result<List<ExpensesGroupModel>>
    suspend fun updateExpenseNotification(
        expenseGroupId: String,
        expenseId: String,
        hasNotification: Boolean
    )
}

class ExpenseRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ExpenseTask {
    override suspend fun getExpenseGroupList(): Result<List<ExpensesGroupModel>> {
        val userId = auth.currentUser?.uid ?: return Result.failure(Exception("User not found"))
        val expenseGroupList = mutableListOf<ExpensesGroupModel>()
        return try {
            val expenseGroupRef = db.collection(UsersCollection.collectionName)
                .document(userId)
                .collection(ExpenseGroupCollection.collectionName)

            val groupSnapshot = expenseGroupRef.get().await()

            for (document in groupSnapshot) {
                val expenseGroupId = document.id
                val createdAt = document.getString("createdAt") ?: ""

                val expenseModelFromDb = mutableListOf<ExpenseModelFromDb>()

                val expenseRef = document.reference.collection(ExpenseCollection.collectionName)
                val expenseSnapshot = expenseRef.get().await()

                for (expense in expenseSnapshot) {
                    val expenseId = expense.id
                    val expenseMap = expense.data
                    val tagMap = expenseMap["tag"] as? Map<*, *> ?: emptyMap<String, Any>()

                    val model = ExpenseModelFromDb(
                        expenseId = expenseId,
                        amount = expenseMap["amount"].toString().toDouble(),
                        dayPay = expenseMap["day"].toString().toIntOrNull(),
                        expenseName = expenseMap["expenseName"].toString(),
                        hasNotification = expenseMap["hasNotification"].toString().toBoolean(),
                        tag = TagModel(
                            tagId = tagMap["tagId"].toString().toInt(),
                            tagNameId = tagMap["tagNameId"].toString(),
                            color = tagMap["color"].toString(),
                            iconId = tagMap["iconId"].toString()
                        )
                    )
                    expenseModelFromDb.add(model)
                }

                expenseGroupList.add(
                    ExpensesGroupModel(
                        expensesGroupId = expenseGroupId,
                        createdAt = createdAt,
                        expenseList = expenseModelFromDb
                    )
                )
            }

            Result.success(expenseGroupList)
        } catch (e: Exception) {
            Log.e(TAG, "getExpenseGroupList: ${e.message}", e)
            Result.failure(e)
        }
    }

    override suspend fun updateExpenseNotification(
        expenseGroupId: String,
        expenseId: String,
        hasNotification: Boolean
    ) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            try {
                val expenseRef = db.collection(UsersCollection.collectionName).document(userId)
                    .collection(ExpenseGroupCollection.collectionName).document(expenseGroupId)
                    .collection(ExpenseCollection.collectionName).document(expenseId)

                expenseRef.update("hasNotification", hasNotification)
            } catch (e: Exception) {
                Log.e(TAG, "updateExpenseNotification: ${e.message}", e)
            }
        }
    }
}