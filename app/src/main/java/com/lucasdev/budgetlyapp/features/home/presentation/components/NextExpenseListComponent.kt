package com.lucasdev.budgetlyapp.features.home.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lucasdev.budgetlyapp.R
import com.lucasdev.budgetlyapp.features.home.domain.models.NextTaskModel
import com.lucasdev.budgetlyapp.ui.theme.AppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NextExpenseListComponent(
    nextTaskList: List<NextTaskModel>,
    modifier: Modifier,
    onSwipeCard: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    if (nextTaskList.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                stringResource(R.string.home_next_expense_title),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                nextTaskList.forEach { nextTaskModel ->
                    key(nextTaskModel.taskId) {
                        var isVisible by remember(nextTaskModel.taskId) { mutableStateOf(true) }
                        AnimatedVisibility(visible = isVisible, exit = shrinkVertically()) {
                            ExpenseHomeBox(nextTaskModel) {
                                coroutineScope.launch {
                                    isVisible = false
                                    delay(1000)
                                    onSwipeCard(it)
                                }
                            }
                        }
                    }
                }
            }
        }
    } else {
        Box(
            modifier = modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.img_relaxation),
                    contentDescription = null
                )

                Text(
                    stringResource(R.string.home_without_next_expenses),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }

}

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun NextExpenseListComponentPreview() {
    AppTheme {
        NextExpenseListComponent(listOf(), Modifier) {}
    }
}