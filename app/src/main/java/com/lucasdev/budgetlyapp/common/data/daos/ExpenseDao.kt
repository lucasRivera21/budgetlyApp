package com.lucasdev.budgetlyapp.common.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.lucasdev.budgetlyapp.common.data.entities.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertExpense(expenseEntity: ExpenseEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertExpenses(expenseEntities: List<ExpenseEntity>)

    @Query("SELECT MAX(expense_id) FROM expenses")
    suspend fun getLastExpenseId(): Int?

    @Query("SELECT COUNT(*) FROM expenses")
    suspend fun countExpenses(): Int

    @Query("SELECT * FROM expenses WHERE is_upload != :upLoadStateCode")
    suspend fun getExpensesToUpload(upLoadStateCode: Int): List<ExpenseEntity>

    @Query("SELECT * FROM expenses WHERE is_upload >= 0 ORDER BY created_at DESC")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    @Query("SELECT is_upload FROM expenses WHERE expense_id = :expenseId")
    suspend fun getIsUpload(expenseId: Int): Int

    @Query("SELECT is_upload FROM expenses WHERE expense_id = :expenseId")
    suspend fun getUploadStateByExpenseId(expenseId: Int): Int

    @Query("UPDATE expenses SET has_notification = :hasNotification, is_upload = :isUpload WHERE expense_id = :expenseId")
    suspend fun updateExpenseNotification(expenseId: Int, hasNotification: Boolean, isUpload: Int)

    @Query("UPDATE expenses SET is_upload = :isUpload WHERE expense_id = :expenseId")
    suspend fun updateExpenseIsUpload(expenseId: Int, isUpload: Int)

    @Query("UPDATE expenses SET is_upload = :isUpload, expense_id_remote = :expenseIdRemote WHERE expense_id = :expenseId")
    suspend fun updateExpensesIsUpload(expenseId: Int, expenseIdRemote: String, isUpload: Int)

    @Query("DELETE FROM expenses WHERE expense_id = :expenseId")
    suspend fun deleteExpenseByExpenseIdLocal(expenseId: Int)

    @Query("DELETE FROM expenses")
    suspend fun deleteAllExpenses()
}