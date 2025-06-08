package com.example.budgetlyapp.features.expense.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.budgetlyapp.R

@Composable
fun NotifyComponent() {
    var isChecked by remember { mutableStateOf(false) }
    val notificationIcon =
        if (!isChecked) R.drawable.ic_notification_off else R.drawable.ic_notification_active
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            stringResource(R.string.create_expense_notification_title),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Medium
        )

        SwitchComponent(isChecked, notificationIcon, Modifier.align(Alignment.CenterHorizontally)) {
            isChecked = it
        }
    }
}