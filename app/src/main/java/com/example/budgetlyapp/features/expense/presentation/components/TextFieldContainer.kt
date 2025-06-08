package com.example.budgetlyapp.features.expense.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.budgetlyapp.R
import com.example.budgetlyapp.common.presentation.components.CustomTextField
import com.example.budgetlyapp.features.expense.presentation.viewModels.CreateExpenseViewModel

@Composable
fun TextFieldContainer(
    nameExpense: String,
    amountExpense: String,
    viewModel: CreateExpenseViewModel
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        CustomTextField(
            textLabel = stringResource(R.string.create_expense_name_input),
            textValue = nameExpense,
            onValueChange = { viewModel.onChaneNameExpense(it) },
            modifier = Modifier.fillMaxWidth()
        )

        CustomTextField(
            textLabel = stringResource(R.string.create_expense_amount_input),
            textValue = amountExpense,
            keyBoardType = KeyboardType.Number,
            onValueChange = { viewModel.onChaneAmountExpense(it) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}