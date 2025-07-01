package com.example.budgetlyapp.features.expense.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.budgetlyapp.R
import com.example.budgetlyapp.common.domain.models.ExpenseModelFromDb
import com.example.budgetlyapp.common.domain.models.TagModel
import com.example.budgetlyapp.common.utils.convertTagIdNameToTagName
import com.example.budgetlyapp.ui.theme.AppTheme
import android.graphics.Color as AndroidColor

@Composable
fun ExpenseComponent(expenseModel: ExpenseModelFromDb) {
    var isChecked by remember { mutableStateOf(expenseModel.hasNotification) }

    val notificationIcon =
        if (!isChecked) R.drawable.ic_notification_off else R.drawable.ic_notification_active

    val monthDayString = if (expenseModel.dayPay != null) "${expenseModel.dayPay} ${
        stringResource(R.string.expense_day_month)
    }" else stringResource(R.string.expense_on_month)

    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //Name
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    expenseModel.expenseName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontWeight = FontWeight.Medium
                )
                Box(
                    modifier = Modifier
                        .background(
                            Color(AndroidColor.parseColor(expenseModel.tag.color)),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        convertTagIdNameToTagName(expenseModel.tag.tagNameId),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )
                }
            }

            //Notification
            SwitchComponent(isChecked, notificationIcon) {
                isChecked = it
            }
        }

        //Amount
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                "$ ${expenseModel.amount}", style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontWeight = FontWeight.Medium
            )
            Text(
                monthDayString, style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Preview(showBackground = true, apiLevel = 33)
@Composable
fun ExpenseComponentPreview() {
    AppTheme {
        ExpenseComponent(
            expenseModel = ExpenseModelFromDb(
                expenseId = "1",
                0.0,
                10,
                "Gym",
                false,
                TagModel(0, "Ahorro", "#7987FF", ""),
            )
        )
    }
}