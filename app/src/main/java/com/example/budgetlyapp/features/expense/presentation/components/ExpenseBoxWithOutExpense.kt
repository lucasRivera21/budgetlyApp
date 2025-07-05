package com.example.budgetlyapp.features.expense.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.budgetlyapp.R
import com.example.budgetlyapp.ui.theme.AppTheme

@Composable
fun ExpenseBoxWithOutExpense(number: Int = 1, onClickWithOutExpenseList: () -> Unit = {}) {
    val textWithOutExpenseList = buildAnnotatedString {
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onSurface)) {
            append(stringResource(R.string.expense_add_expense))
        }
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
            append(" ${stringResource(R.string.expense_here)}")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            "${stringResource(R.string.expense_subtitle_expenses)} $number",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            textWithOutExpenseList,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.clickable { onClickWithOutExpenseList() }
        )
    }
}

@Preview(apiLevel = 33, showBackground = true)
@Composable
fun Preview() {
    AppTheme {
        ExpenseBoxWithOutExpense()
    }
}