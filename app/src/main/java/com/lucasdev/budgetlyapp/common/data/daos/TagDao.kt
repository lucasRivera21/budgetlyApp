package com.lucasdev.budgetlyapp.common.data.daos

import androidx.room.Dao
import androidx.room.Insert
import com.lucasdev.budgetlyapp.common.data.entities.TagEntity

@Dao
interface TagDao {
    @Insert
    suspend fun insertTag(tagEntity: TagEntity)
}