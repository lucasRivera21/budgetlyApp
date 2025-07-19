package com.example.budgetlyapp.common.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.budgetlyapp.R
import java.text.DecimalFormat
import java.text.NumberFormat
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

@Composable
fun convertTagIdNameToTagName(tagIdName: String): String {
    return when (tagIdName) {
        "tag_saving" -> stringResource(R.string.tag_saving)
        "tag_home" -> stringResource(R.string.tag_home)
        "tag_health" -> stringResource(R.string.tag_health)
        "tag_pet" -> stringResource(R.string.tag_pet)
        "tag_transport" -> stringResource(R.string.tag_transport)
        "tag_market" -> stringResource(R.string.tag_market)
        "tag_sure" -> stringResource(R.string.tag_sure)
        "tag_study" -> stringResource(R.string.tag_study)
        "tag_subscription" -> stringResource(R.string.tag_subscription)
        "tag_other" -> stringResource(R.string.tag_other)
        else -> tagIdName
    }
}

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