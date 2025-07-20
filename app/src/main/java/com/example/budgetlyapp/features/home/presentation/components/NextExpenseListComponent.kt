package com.example.budgetlyapp.features.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.budgetlyapp.R
import com.example.budgetlyapp.features.home.domain.models.ExpenseHomeModel
import com.example.budgetlyapp.ui.theme.AppTheme

@Composable
fun NextExpenseListComponent() {
    val expenseHomeModel = listOf(
        ExpenseHomeModel(
            0,
            "1 ene 2025",
            "Retrasado",
            R.drawable.ic_savings_category,
            Color.Blue,
            "Arriendo",
            "$1,000,000.00"
        ),
        ExpenseHomeModel(
            1,
            "2 ene 2025",
            "Retrasado",
            R.drawable.ic_health_category,
            Color.Red,
            "Gym",
            "$500,000"
        ),
        ExpenseHomeModel(
            2,
            "3 ene 2025",
            "",
            R.drawable.ic_market_category,
            Color.Green,
            "Mercado",
            "$180,000"
        ),
        ExpenseHomeModel(
            3,
            "3 ene 2025",
            "",
            R.drawable.ic_market_category,
            Color.Green,
            "Mercado",
            "$180,000"
        ),
        ExpenseHomeModel(
            4,
            "3 ene 2025",
            "",
            R.drawable.ic_market_category,
            Color.Green,
            "Mercado",
            "$180,000"
        )
    )
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
            expenseHomeModel.forEach { expenseHomeModel ->
                ExpenseHomeBox(expenseHomeModel)
            }
        }
    }
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun NextExpenseListComponentPreview() {
    AppTheme {
        NextExpenseListComponent()
    }
}