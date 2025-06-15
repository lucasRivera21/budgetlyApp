package com.example.budgetlyapp.features.expense.data.repository

import android.util.Log
import com.example.budgetlyapp.ExpenseCollection
import com.example.budgetlyapp.ExpenseGroupCollection
import com.example.budgetlyapp.UsersCollection
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

interface ExpenseTask {
    suspend fun getExpenseGroupList()
}

class ExpenseRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ExpenseTask {
    override suspend fun getExpenseGroupList() {
        val userId = auth.currentUser?.uid ?: return

        Log.d("ExpenseRepository", "getExpenseGroupList: $userId")
        val expenseGroupRef = db.collection(UsersCollection.collectionName).document(userId)
            .collection(ExpenseGroupCollection.collectionName)

        expenseGroupRef.get().addOnSuccessListener { result ->
            Log.d("ExpenseRepository", "getExpenseGroupList: ${result.size()}")
            for (document in result) {
                Log.d("ExpenseRepository", "getExpenseGroupList: ${document.data}")
                val expenseRef = document.reference.collection(ExpenseCollection.collectionName)
                expenseRef.get().addOnSuccessListener { expenseResult ->
                    expenseResult.map {
                        Log.d("ExpenseRepository", "getExpenseGroupList: ${it.data}")
                    }
                }
            }
        }.addOnFailureListener { exception ->
            Log.e("ExpenseRepository", "getExpenseGroupList: ${exception.message}", exception)
        }
    }
}