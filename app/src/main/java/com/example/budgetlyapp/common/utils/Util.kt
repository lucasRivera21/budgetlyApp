package com.example.budgetlyapp.common.utils

import com.example.budgetlyapp.R
import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.UUID

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

fun convertDayMonthYearToDate(day: Int, month: Int, year: Int): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())

    val yearMonth = YearMonth.of(year, month)
    val lastDayOfMonth = yearMonth.lengthOfMonth()

    val safeDay = minOf(day, lastDayOfMonth)

    val date = LocalDate.of(year, month, safeDay)
    return date.format(formatter)
}

fun changeFormatDate(date: String, oldFormat: String, newFormat: String): String {
    val formatterOld = DateTimeFormatter.ofPattern(oldFormat, Locale.getDefault())
    val formatterNew = DateTimeFormatter.ofPattern(newFormat, Locale.getDefault())
    val localDate = LocalDate.parse(date, formatterOld)
    return localDate.format(formatterNew)
}

fun convertTagIdNameToTagName(tagIdName: String): Int {
    return when (tagIdName) {
        "tag_saving" -> R.string.tag_saving
        "tag_home" -> R.string.tag_home
        "tag_health" -> R.string.tag_health
        "tag_pet" -> R.string.tag_pet
        "tag_transport" -> R.string.tag_transport
        "tag_market" -> R.string.tag_market
        "tag_sure" -> R.string.tag_sure
        "tag_study" -> R.string.tag_study
        "tag_subscription" -> R.string.tag_subscription
        else -> R.string.tag_other
    }
}

fun getPercentage(value: Double, total: Double): Double = value * 100 / total

fun formatDecimal(value: Double): String {
    val formatter = NumberFormat.getInstance(Locale("en", "EU"))
    formatter.minimumFractionDigits = 2
    formatter.maximumFractionDigits = 2
    return formatter.format(value)
}

fun Double.formatThousand(): String {
    val decimalFormatter = DecimalFormat("#,###")
    return decimalFormatter.format(this)
}

fun String.clearThousandFormat(): String {
    return this.replace(",", "").replace(" ", "").replace("-", "").replace(".", ".0")
}

fun String.upperFirstChar(): String =
    this.mapIndexed { index, c -> if (index == 0) c.uppercaseChar() else c }.joinToString("")

fun getNewUuid(): String = UUID.randomUUID().toString()