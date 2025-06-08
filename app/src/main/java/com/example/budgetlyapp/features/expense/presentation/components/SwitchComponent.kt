package com.example.budgetlyapp.features.expense.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun SwitchComponent(isChecked: Boolean, notificationIcon: Int, modifier: Modifier = Modifier, onCheckedChange: (Boolean) -> Unit) {
    Switch(checked = isChecked, modifier = modifier, onCheckedChange = {
        onCheckedChange(it)
    }, thumbContent = {
        Icon(
            painter = painterResource(notificationIcon), "notification",
            modifier = Modifier.size(
                SwitchDefaults.IconSize
            ),
        )
    })
}