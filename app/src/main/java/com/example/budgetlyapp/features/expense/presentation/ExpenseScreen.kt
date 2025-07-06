package com.example.budgetlyapp.features.expense.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.budgetlyapp.R
import com.example.budgetlyapp.features.expense.presentation.components.ExpenseBox
import com.example.budgetlyapp.features.expense.presentation.components.ExpenseBoxWithOutExpense
import com.example.budgetlyapp.features.expense.presentation.viewModels.ExpenseViewModel
import com.example.budgetlyapp.navigation.CreateExpenseScreen

@Composable
fun ExpenseScreen(
    globalNavController: NavHostController,
    viewModel: ExpenseViewModel = hiltViewModel()
) {
    val expenseGroupList by viewModel.expenseGroupList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val showDialog by viewModel.showDialog.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getExpenseGroupList()
    }

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

        if (!isLoading) {
            if (expenseGroupList.isEmpty()) {
                ExpenseBoxWithOutExpense {
                    globalNavController.navigate(
                        "${CreateExpenseScreen.route}/"
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                itemsIndexed(expenseGroupList) { index, item ->
                    ExpenseBox(
                        number = index + 1,
                        expensesGroupModel = item,
                        viewModel = viewModel,
                        onClickWithExpenseList = { globalNavController.navigate("${CreateExpenseScreen.route}/${item.expensesGroupId}") }
                    )

                    if (index == expenseGroupList.lastIndex) {
                        Spacer(Modifier.size(24.dp))

                        ExpenseBoxWithOutExpense(expenseGroupList.size + 1) {
                            globalNavController.navigate(
                                "${CreateExpenseScreen.route}/"
                            )
                        }
                    }
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(50.dp)
                )
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                viewModel.cancelDialog()
            },
            confirmButton = {
                TextButton(onClick = { viewModel.acceptDialog() }) {
                    Text(text = stringResource(R.string.dialog_accept))
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.cancelDialog() }) {
                    Text(text = stringResource(R.string.dialog_cancel))
                }
            },
            title = {
                Text(
                    text = stringResource(R.string.dialog_title_delete)
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.dialog_text_delete)
                )
            }
        )
    }
}