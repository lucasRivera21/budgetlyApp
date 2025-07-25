package com.example.budgetlyapp.features.home.data

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

interface HomeTask {
    suspend fun fetchHomeData(): List<ExpensesGroupModel>
}

class HomeRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) : HomeTask {
    override suspend fun fetchHomeData(): List<ExpensesGroupModel> {
        val userId = auth.currentUser!!.uid

        val userRef = db.collection(UsersCollection.collectionName).document(userId)
        val expenseGroupRef = userRef.collection(ExpenseGroupCollection.collectionName)

        val expenseGroupList = mutableListOf<ExpensesGroupModel>()
        val expenseGroupSnapshot = expenseGroupRef.get().await()
        for (document in expenseGroupSnapshot) {
            val expenseGroupId = document.id
            val createdAt = document.getString("createdAt") ?: ""

            val expenseRef = document.reference.collection(ExpenseCollection.collectionName)
            val expenseSnapshot = expenseRef.get().await()
            val expenseList = mutableListOf<ExpenseModelFromDb>()

            for (expense in expenseSnapshot) {
                val expenseId = expense.id
                val expenseData = expense.data
                val tagMap = expenseData["tag"] as? Map<*, *> ?: emptyMap<String, Any>()
                val expenseModel = ExpenseModelFromDb(
                    expenseId = expenseId,
                    amount = expenseData["amount"].toString().toDouble(),
                    dayPay = expenseData["day"].toString().toIntOrNull(),
                    expenseName = expenseData["expenseName"].toString(),
                    hasNotification = expenseData["hasNotification"].toString().toBoolean(),
                    createdAt = expenseData["createdAt"].toString(),
                    tag = TagModel(
                        tagId = tagMap["tagId"].toString().toInt(),
                        tagNameId = tagMap["tagNameId"].toString(),
                        color = tagMap["color"].toString(),
                        iconId = tagMap["iconId"].toString()
                    )
                )
                expenseList.add(expenseModel)
            }
            expenseGroupList.add(
                ExpensesGroupModel(
                    expensesGroupId = expenseGroupId,
                    createdAt = createdAt,
                    expenseList = expenseList
                )
            )
        }
        return expenseGroupList
    }
}

