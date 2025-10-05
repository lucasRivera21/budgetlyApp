package com.lucasdev.budgetlyapp.common.data.daos

import androidx.room.Dao
import androidx.room.Insert
import com.lucasdev.budgetlyapp.common.data.entities.ExpenseEntity

@Dao
interface ExpenseDao {
    @Insert
    suspend fun insertExpense(expenseEntity: ExpenseEntity)
}