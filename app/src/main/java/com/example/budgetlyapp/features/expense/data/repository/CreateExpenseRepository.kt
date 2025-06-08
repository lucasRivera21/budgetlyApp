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

        val expenseRef = expenseGroupRef.document().collection(ExpenseCollection.collectionName)

        return try {
            expenseRef.add(expenseModel).await()
            Result.success(expenseRef.id)
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            Result.failure(e)
        }
    }
}