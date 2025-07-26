package com.example.budgetlyapp.features.expense.data.repository

import android.util.Log
import com.example.budgetlyapp.ExpenseCollection
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
    override suspend fun createTask(
        expenseModel: ExpenseModel
    ): Result<String> {
        val userId = auth.currentUser?.uid

        val expenseRef =
            db.collection(UsersCollection.collectionName).document(userId!!).collection(
                ExpenseCollection.collectionName
            )

        return try {
            val expenseSnapshot = expenseRef.add(expenseModel).await()
            Result.success(expenseSnapshot.id)
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            Result.failure(e)
        }
    }
}