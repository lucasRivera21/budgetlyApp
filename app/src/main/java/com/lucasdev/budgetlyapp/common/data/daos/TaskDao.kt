package com.lucasdev.budgetlyapp.common.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lucasdev.budgetlyapp.common.data.entities.TaskEntity
import com.lucasdev.budgetlyapp.features.expense.data.dto.TaskToUploadNotificationDTO

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

    @Query("UPDATE tasks SET request_code = :requestCode WHERE expense_id = :expenseId AND date_due = :dateDue")
    suspend fun updateTaskRequestCode(expenseId: Int, requestCode: Int?, dateDue: String)

    @Query("UPDATE tasks SET is_upload = :isUpload WHERE expense_id = :expenseId")
    suspend fun updateTaskUploadState(expenseId: Int, isUpload: Int)
}