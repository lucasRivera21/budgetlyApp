package com.example.budgetlyapp.features.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.budgetlyapp.R
import com.example.budgetlyapp.features.home.domain.models.NextTaskModel
import com.example.budgetlyapp.ui.theme.AppTheme

@Composable
fun NextExpenseListComponent(nextTaskList: List<NextTaskModel>, onSwipeCard: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Text(
            stringResource(R.string.home_next_expense_title),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            nextTaskList.forEach { nextTaskModel ->
                key(nextTaskModel.taskId) {
                    ExpenseHomeBox(nextTaskModel) {
                        onSwipeCard(it)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun NextExpenseListComponentPreview() {
    AppTheme {
    }
}