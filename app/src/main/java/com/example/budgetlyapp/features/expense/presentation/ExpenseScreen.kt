package com.example.budgetlyapp.features.expense.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.budgetlyapp.R
import com.example.budgetlyapp.common.domain.models.ExpensesGroupModel
import com.example.budgetlyapp.features.expense.presentation.components.ExpenseBox
import com.example.budgetlyapp.navigation.CreateExpenseScreen

@Composable
fun ExpenseScreen(globalNavController: NavHostController) {
    val expenseGroupList = listOf(
        ExpensesGroupModel(
            1, 0, listOf(
            ), 0.0
        )
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.expense_title),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold
        )

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(expenseGroupList) {
                ExpenseBox(it) {
                    globalNavController.navigate(CreateExpenseScreen.route)
                }
            }
        }

    }
}