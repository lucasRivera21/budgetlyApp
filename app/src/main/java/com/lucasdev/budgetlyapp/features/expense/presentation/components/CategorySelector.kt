package com.lucasdev.budgetlyapp.features.expense.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lucasdev.budgetlyapp.R
import com.lucasdev.budgetlyapp.common.domain.models.CategoryProvider
import com.lucasdev.budgetlyapp.common.domain.models.TagModel
import com.lucasdev.budgetlyapp.features.expense.presentation.viewModels.CreateExpenseViewModel

@Composable
fun CategorySelector(categorySelected: TagModel, viewModel: CreateExpenseViewModel) {
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
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 300.dp)
        ) {
            items(categories) { tagModel ->
                CategoryComponent(tagModel, categorySelected.tagId) {
                    viewModel.onChangeCategorySelected(tagModel)
                }
            }
        }
    }
}