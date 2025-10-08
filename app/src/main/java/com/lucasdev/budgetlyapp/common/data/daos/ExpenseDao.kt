package com.lucasdev.budgetlyapp.common.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lucasdev.budgetlyapp.common.data.entities.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Insert
    suspend fun insertExpense(expenseEntity: ExpenseEntity)

    @Query("SELECT MAX(expense_id) FROM expenses")
    suspend fun getLastExpenseId(): Int?

    @Query("SELECT * FROM expenses")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>
}