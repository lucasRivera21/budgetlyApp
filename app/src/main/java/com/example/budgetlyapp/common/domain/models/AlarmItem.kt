package com.example.budgetlyapp.common.domain.models

import java.time.LocalDateTime

data class AlarmItem(
    val requestCode: Int,
    val alarmTime: LocalDateTime,
    val icon: Int,
    val title: String,
    val message: String
)
