package com.example.budgetlyapp.features.expense.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.budgetlyapp.R
import com.example.budgetlyapp.features.expense.presentation.viewModels.CreateExpenseViewModel

@Composable
fun NotifyComponent(hasNotification: Boolean, viewModel: CreateExpenseViewModel) {
    val notificationIcon =
        if (!hasNotification) R.drawable.ic_notification_off else R.drawable.ic_notification_active
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            stringResource(R.string.create_expense_notification_title),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Medium
        )

        SwitchComponent(
            hasNotification,
            notificationIcon,
            Modifier.align(Alignment.CenterHorizontally)
        ) {
            viewModel.onChangeHasNotification(it)
        }
    }
}