package com.lucasdev.budgetlyapp.common.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lucasdev.budgetlyapp.common.data.entities.TaskEntity
import com.lucasdev.budgetlyapp.features.expense.data.dto.TaskToUploadNotificationDTO
import com.lucasdev.budgetlyapp.features.home.data.NextExpenseDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert
    suspend fun insertTasks(tasks: List<TaskEntity>)

    @Insert
    suspend fun insertTask(taskEntity: TaskEntity)

    @Query(
        """
        SELECT 
            t.task_id,
            t.request_code, 
            e.tag_id, 
            e.expense_name, 
            e.expense_amount as amount, 
            t.date_due 
            FROM tasks t INNER JOIN expenses e ON t.expense_id = e.expense_id
            WHERE t.expense_id = :expenseId
    """
    )
    suspend fun getTasks(expenseId: Int): List<TaskToUploadNotificationDTO>

    @Query("SELECT request_code FROM tasks WHERE task_id = :taskId")
    suspend fun getRequestCode(taskId: Int): Int?

    @Query("UPDATE tasks SET request_code = :requestCode WHERE expense_id = :expenseId AND date_due = :dateDue")
    suspend fun updateTaskRequestCode(expenseId: Int, requestCode: Int?, dateDue: String)

    @Query("UPDATE tasks SET is_upload = :isUpload WHERE expense_id = :expenseId")
    suspend fun updateTaskUploadState(expenseId: Int, isUpload: Int)

    @Query(
        """
        SELECT 
            t.task_id as taskId,
            e.expense_amount as amount,
            t.is_complete as isCompleted,
            t.created_at as createdAt,
            t.date_due as dateDue,
            e.expense_group_id as expenseGroupId,
            e.expense_id as expenseId,
            t.request_code as requestCode,
            e.day as dayDue,
            e.has_notification as hasNotification,
            e.expense_name as expenseName,
            e.tag_id as tagId
        FROM tasks t INNER JOIN expenses e ON t.expense_id = e.expense_id
        WHERE e.is_upload >= 0 AND is_complete = 0
        ORDER BY t.date_due ASC
    """
    )
    fun getNextTask(): Flow<List<NextExpenseDTO>>

    @Query("""
    SELECT 
        t.task_id as taskId,
        e.expense_amount as amount,
        t.is_complete as isCompleted,
        t.created_at as createdAt,
        t.date_due as dateDue,
        e.expense_group_id as expenseGroupId,
        t.expense_id as expenseId,
        t.request_code as requestCode,
        e.day as dayDue,
        e.has_notification as hasNotification,
        e.expense_name as expenseName,
        e.tag_id as tagId
    FROM tasks t
    JOIN expenses e ON e.expense_id = t.expense_id
    WHERE t.expense_id IN (1, 2)
    AND t.date_due = (
        SELECT MAX(t2.date_due)
        FROM tasks t2
        WHERE t2.expense_id = t.expense_id
    )
    ORDER BY t.date_due DESC;
    """)
    fun getLatestTasks(): List<NextExpenseDTO>

    @Query("UPDATE tasks SET is_complete = :isCompleted WHERE task_id = :taskId")
    suspend fun updateTaskCompletion(taskId: Int, isCompleted: Boolean)
}