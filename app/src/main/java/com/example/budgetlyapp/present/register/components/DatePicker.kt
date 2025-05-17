package com.example.budgetlyapp.present.register.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chargemap.compose.numberpicker.ListItemPicker
import com.example.budgetlyapp.R
import com.example.budgetlyapp.ui.theme.AppTheme

@Composable
fun DatePicker() {
    val monthList = listOf(
        stringResource(R.string.register_month_jan),
        stringResource(R.string.register_month_feb),
        stringResource(R.string.register_month_mar),
        stringResource(R.string.register_month_abr),
        stringResource(R.string.register_month_may),
        stringResource(R.string.register_month_jun),
        stringResource(R.string.register_month_jul),
        stringResource(R.string.register_month_aug),
        stringResource(R.string.register_month_sep),
        stringResource(R.string.register_month_oct),
        stringResource(R.string.register_month_nov),
        stringResource(R.string.register_month_dic)
    )
    val dayList = (1..31).map { it.toString() }
    val yearList = (2023..2050).map { it.toString() }

    var stateMonth by remember { mutableStateOf(monthList[0]) }
    var stateDay by remember { mutableStateOf(dayList[0]) }
    var stateYear by remember { mutableStateOf(yearList[0]) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ListItemPicker(
            label = { it },
            value = stateMonth,
            onValueChange = { stateMonth = it },
            list = monthList,
            dividersColor = MaterialTheme.colorScheme.primary
        )

        ListItemPicker(
            label = { it },
            value = stateDay,
            onValueChange = { stateDay = it },
            list = dayList,
            dividersColor = MaterialTheme.colorScheme.primary
        )

        ListItemPicker(
            label = { it },
            value = stateYear,
            onValueChange = { stateYear = it },
            list = yearList,
            dividersColor = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun DatePickerPreview() {
    AppTheme {
        DatePicker()
    }
}