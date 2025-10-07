package com.lucasdev.budgetlyapp.common.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "expense_id")
    val expenseId: Int = 0,
    @ColumnInfo(name = "expense_name")
    val expenseName: String,
    @ColumnInfo(name = "expense_amount")
    val expenseAmount: String,
    @ColumnInfo(name = "created_at")
    val createdAt: String,
    val day: Int?,
    @ColumnInfo(name = "expense_group_id")
    val expenseGroupId: String,
    @ColumnInfo(name = "has_notification")
    val hasNotification: Boolean,
    @ColumnInfo(name = "tag_id")
    val tagId: Int,
    @ColumnInfo(name = "is_upload")
    val isUpload: Int = 0
)
