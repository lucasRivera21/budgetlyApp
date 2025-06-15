package com.example.budgetlyapp.features.expense.data.repository

import android.util.Log
import com.example.budgetlyapp.ExpenseCollection
import com.example.budgetlyapp.ExpenseGroupCollection
import com.example.budgetlyapp.UsersCollection
import com.example.budgetlyapp.common.domain.models.ExpenseModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "CreateExpenseRepository"

interface CreateExpenseTask {
    suspend fun createTask(expenseModel: ExpenseModel): Result<String>
}

class CreateExpenseRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) :
    CreateExpenseTask {
    override suspend fun createTask(expenseModel: ExpenseModel): Result<String> {
        val uuid = auth.currentUser?.uid

        val expenseGroupRef =
            db.collection(UsersCollection.collectionName).document(uuid!!).collection(
                ExpenseGroupCollection.collectionName
            )

        return try {
            var expenseGroupSize = 0
            expenseGroupRef.get().addOnSuccessListener { result ->
                expenseGroupSize = result.size()
            }

            var expenseGroupId: String? = null
            val expenseGroupMap = mapOf<String, Any>(
                "order" to expenseGroupSize,
            )
            expenseGroupRef.add(expenseGroupMap).addOnSuccessListener {
                expenseGroupId = it.id
            }.addOnFailureListener {
                Log.e(TAG, it.message, it)
            }.await()
            if (expenseGroupId != null) {
                val expenseRef = expenseGroupRef.document(expenseGroupId!!)
                    .collection(ExpenseCollection.collectionName)
                expenseRef.add(expenseModel).await()
                Result.success(expenseRef.id)
            } else {
                Result.failure(Exception("Expense group ID is null"))
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            Result.failure(e)
        }
    }
}