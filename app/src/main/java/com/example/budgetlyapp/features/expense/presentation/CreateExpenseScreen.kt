package com.example.budgetlyapp.features.expense.presentation

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.budgetlyapp.R
import com.example.budgetlyapp.features.expense.presentation.components.CategorySelector
import com.example.budgetlyapp.features.expense.presentation.components.DayPayContainer
import com.example.budgetlyapp.features.expense.presentation.components.Header
import com.example.budgetlyapp.features.expense.presentation.components.NotifyComponent
import com.example.budgetlyapp.features.expense.presentation.components.TextFieldContainer
import com.example.budgetlyapp.features.expense.presentation.viewModels.CreateExpenseViewModel
import com.example.budgetlyapp.ui.theme.AppTheme

@Composable
fun CreateExpenseScreen(
    expenseGroupId: String?,
    navController: NavHostController,
    viewModel: CreateExpenseViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    val nameExpense by viewModel.nameExpense.collectAsState()
    val amountExpense by viewModel.amountExpense.collectAsState()
    val categorySelected by viewModel.categorySelected.collectAsState()
    val dayPayString by viewModel.dayPayString.collectAsState()
    val hasDayPay by viewModel.hasDayPay.collectAsState()
    val hasNotification by viewModel.hasNotification.collectAsState()

    Scaffold(floatingActionButton = {
        ExtendedFloatingActionButton(
            text = {
                Text(
                    stringResource(R.string.create_expense_save),
                    style = MaterialTheme.typography.labelLarge
                )
            },
            icon = {
                Icon(imageVector = Icons.Default.Check, "save")
            },
            onClick = { viewModel.onClickSave(expenseGroupId, navController) },
        )
    }) { _ ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .scrollable(scrollState, orientation = Orientation.Vertical),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Header(navController)

            TextFieldContainer(nameExpense, amountExpense, viewModel)

            CategorySelector(categorySelected, viewModel)

            DayPayContainer(dayPayString, hasDayPay, viewModel)

            NotifyComponent(hasNotification, viewModel)
        }
    }


}

@Preview(showBackground = true, apiLevel = 33)
@Composable
fun CreateExpenseScreenPreview() {
    AppTheme {
        CreateExpenseScreen(null, NavHostController(LocalContext.current))
    }
}