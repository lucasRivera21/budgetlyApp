package com.lucasdev.budgetlyapp.common.data.daos

import androidx.room.Dao
import androidx.room.Insert
import com.lucasdev.budgetlyapp.common.data.entities.TaskEntity

@Dao
interface TaskDao {
    @Insert
    suspend fun insertTask(taskEntity: TaskEntity)
}