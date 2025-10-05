package com.lucasdev.budgetlyapp.common.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tags")
data class TagEntity(
    @PrimaryKey
    @ColumnInfo(name = "tag_id")
    val tagId: Int,
    @ColumnInfo(name = "tag_name_id")
    val tagNameId: String,
    @ColumnInfo(name = "tag_color")
    val tagColor: String,
    @ColumnInfo(name = "tag_icon")
    val tagIcon: String
)
