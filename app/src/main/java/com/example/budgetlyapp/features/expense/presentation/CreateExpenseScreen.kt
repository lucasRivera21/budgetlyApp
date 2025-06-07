package com.example.budgetlyapp.features.expense.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.budgetlyapp.R
import com.example.budgetlyapp.common.domain.models.CategoryProvider
import com.example.budgetlyapp.common.presentation.components.CustomTextField
import com.example.budgetlyapp.features.expense.presentation.components.CategoryComponent
import com.example.budgetlyapp.ui.theme.AppTheme

@Composable
fun CreateExpenseScreen(navController: NavHostController) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Header(navController)

        TextFieldContainer()

        CategorySelector()
    }
}

@Composable
fun Header(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxWidth()) {
        IconButton(onClick = {
            navController.popBackStack()
        }, modifier = Modifier.align(Alignment.CenterStart)) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_back),
                contentDescription = "Back Icon"
            )
        }
        Text(
            text = stringResource(R.string.create_expense_title),
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun TextFieldContainer() {
    var text by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        CustomTextField(
            textLabel = stringResource(R.string.create_expense_name_input),
            textValue = text,
            onValueChange = { text = it },
            modifier = Modifier.fillMaxWidth()
        )

        CustomTextField(
            textLabel = stringResource(R.string.create_expense_amount_input),
            textValue = number,
            keyBoardType = KeyboardType.Number,
            onValueChange = { number = it },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun CategorySelector() {
    val categories = CategoryProvider.categories

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            stringResource(R.string.create_expense_category),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Medium
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(50.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { tagModel ->
                CategoryComponent(tagModel)
            }
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