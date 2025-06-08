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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.budgetlyapp.R
import com.example.budgetlyapp.features.expense.presentation.components.CategorySelector
import com.example.budgetlyapp.features.expense.presentation.components.DayPayContainer
import com.example.budgetlyapp.features.expense.presentation.components.Header
import com.example.budgetlyapp.features.expense.presentation.components.NotifyComponent
import com.example.budgetlyapp.features.expense.presentation.components.TextFieldContainer
import com.example.budgetlyapp.ui.theme.AppTheme

@Composable
fun CreateExpenseScreen(navController: NavHostController) {
    val scrollState = rememberScrollState()

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
            onClick = { /*TODO*/ },
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

            TextFieldContainer()

            CategorySelector()

            DayPayContainer()

            NotifyComponent()
        }
    }


}

@Preview(showBackground = true, apiLevel = 33)
@Composable
fun CreateExpenseScreenPreview() {
    AppTheme {
        CreateExpenseScreen(NavHostController(LocalContext.current))
    }
}