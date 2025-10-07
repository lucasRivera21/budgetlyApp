package com.lucasdev.budgetlyapp.common.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lucasdev.budgetlyapp.common.data.daos.ExpenseDao
import com.lucasdev.budgetlyapp.common.data.daos.TaskDao
import com.lucasdev.budgetlyapp.common.data.entities.ExpenseEntity
import com.lucasdev.budgetlyapp.common.data.entities.TaskEntity
import com.lucasdev.budgetlyapp.common.utils.DATABASE_VERSION

@Database(
    entities = [ExpenseEntity::class, TaskEntity::class],
    version = DATABASE_VERSION
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun taskDao(): TaskDao
}