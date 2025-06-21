package com.example.budgetlyapp.common.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

enum class MoneyType {
    USD {
        override fun returnMoneyType() = "USD"
    },
    COP {
        override fun returnMoneyType() = "COP"
    },
    EUR {
        override fun returnMoneyType() = "EUR"
    };

    abstract fun returnMoneyType(): String
}

fun getTodayDate(format: String = "yyyy-MM-dd HH:mm:ss"): String {
    val formatter = DateTimeFormatter.ofPattern(format, Locale.getDefault())
    return LocalDateTime.now().format(formatter)
}