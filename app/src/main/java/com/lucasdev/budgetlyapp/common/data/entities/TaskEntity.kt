package com.lucasdev.budgetlyapp.common.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tasks",
    foreignKeys = [ForeignKey(
        entity = ExpenseEntity::class,
        parentColumns = ["expense_id"],
        childColumns = ["expense_id"],
        onDelete = CASCADE
    )],
    indices = [Index(value = ["expense_id"])]
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    val taskId: Int,
    @ColumnInfo(name = "is_complete")
    val isComplete: Int,
    @ColumnInfo(name = "created_at")
    val createdAt: String,
    @ColumnInfo(name = "date_due")
    val dateDue: String,
    @ColumnInfo(name = "expense_id")
    val expenseId: Int,
    @ColumnInfo(name = "request_code")
    val requestCode: Int,
    @ColumnInfo(name = "is_upload")
    val isUpload: Int
)
