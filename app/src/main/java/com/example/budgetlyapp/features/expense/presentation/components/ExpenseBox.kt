package com.example.budgetlyapp.features.expense.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.budgetlyapp.R
import com.example.budgetlyapp.common.domain.models.ExpenseModelFromDb
import com.example.budgetlyapp.common.domain.models.ExpensesGroupModel
import com.example.budgetlyapp.common.domain.models.TagModel
import com.example.budgetlyapp.common.utils.formatDecimal
import com.example.budgetlyapp.ui.theme.AppTheme

@Composable
fun ExpenseBox(
    number: Int,
    expensesGroupModel: ExpensesGroupModel,
    onClickNotificationSwitch: (String, String, Boolean) -> Unit = { _, _, _ -> },
    onClickWithExpenseList: () -> Unit = {},
) {
    val sumAmount = expensesGroupModel.expenseList.sumOf { it.amount }

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

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
        ) {
            expensesGroupModel.expenseList.forEachIndexed { index, expenseModel ->
                ExpenseComponent(
                    expenseModel = expenseModel,
                    onClickNotificationSwitch = {
                        onClickNotificationSwitch(
                            expensesGroupModel.expensesGroupId,
                            expenseModel.expenseId,
                            it
                        )
                    })

                if (index != expensesGroupModel.expenseList.lastIndex) {
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.outlineVariant,
                        thickness = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "+ ${stringResource(R.string.expense_new_expense)}",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    onClickWithExpenseList()
                }
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "${stringResource(R.string.expense_total_expense)}: $ ${
                        formatDecimal(
                            sumAmount
                        )
                    }",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    "",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Preview(showBackground = true, apiLevel = 33)
@Composable
fun ExpenseBoxPreview() {
    AppTheme {
        ExpenseBox(
            1,
            ExpensesGroupModel(
                "1", "", listOf(
                    ExpenseModelFromDb(
                        expenseId = "1",
                        1000000.0,
                        10,
                        "Gym",
                        true,
                        TagModel(3, "tag_health", "#90BE6D", "ic_health_category"),
                        createdAt = ""
                    ),
                )
            )
        )
    }
}