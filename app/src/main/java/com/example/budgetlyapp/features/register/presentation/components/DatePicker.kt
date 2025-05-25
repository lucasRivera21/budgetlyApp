package com.example.budgetlyapp.features.register.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chargemap.compose.numberpicker.ListItemPicker
import com.example.budgetlyapp.features.register.presentation.RegisterViewModel

@Composable
fun DatePicker(
    dayBirth: String,
    monthBirth: String,
    yearBirth: String,
    registerViewModel: RegisterViewModel
) {
    val monthList = registerViewModel.returnMonthList().map { stringResource(it) }
    val dayList = registerViewModel.returnDayList().map { it.toString() }
    val yearList = registerViewModel.returnYearList().map { it.toString() }

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ListItemPicker(
            label = { it },
            value = monthBirth,
            onValueChange = {
                registerViewModel.onMonthBirthChange(it)
                registerViewModel.onSelectedMonthBirthChange(monthList.indexOf(it))

            },
            list = monthList,
            dividersColor = MaterialTheme.colorScheme.primary
        )

        ListItemPicker(
            label = { it },
            value = dayBirth,
            onValueChange = {
                registerViewModel.onDayBirthChange(it)
            },
            list = dayList,
            dividersColor = MaterialTheme.colorScheme.primary
        )

        ListItemPicker(
            label = { it },
            value = yearBirth,
            onValueChange = {
                registerViewModel.onYearBirthChange(it)
            },
            list = yearList,
            dividersColor = MaterialTheme.colorScheme.primary
        )
    }
}