package com.example.budgetlyapp.features.expense.data.repository

import com.example.budgetlyapp.ExpenseCollection
import com.example.budgetlyapp.TaskCollection
import com.example.budgetlyapp.UsersCollection
import com.example.budgetlyapp.common.domain.models.ExpenseModel
import com.example.budgetlyapp.features.expense.domain.models.TaskUpload
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface CreateExpenseTask {
    suspend fun createExpense(expenseModel: ExpenseModel): String
    suspend fun createTask(taskList: List<TaskUpload>)
}

class CreateExpenseRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) :
    CreateExpenseTask {
    override suspend fun createExpense(
        expenseModel: ExpenseModel
    ): String {
        val userId = auth.currentUser?.uid

        val userRef = db.collection(UsersCollection.collectionName).document(userId!!)
        val expenseRef = userRef.collection(ExpenseCollection.collectionName)

        val expenseSnapshot = expenseRef.add(expenseModel).await()
        return expenseSnapshot.id
    }

    override suspend fun createTask(taskList: List<TaskUpload>) {
        val userId = auth.currentUser?.uid
        val userRef = db.collection(UsersCollection.collectionName).document(userId!!)
        val taskRef = userRef.collection(TaskCollection.collectionName)

        taskList.forEach { task ->
            taskRef.add(task)
        }
    }
}