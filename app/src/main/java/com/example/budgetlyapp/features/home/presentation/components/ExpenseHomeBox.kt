package com.example.budgetlyapp.features.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.budgetlyapp.R
import com.example.budgetlyapp.features.home.domain.models.ExpenseHomeModel
import com.example.budgetlyapp.ui.theme.AppTheme

@Composable
fun ExpenseHomeBox(expenseHomeModel: ExpenseHomeModel) {
    val expenseColor = expenseHomeModel.color
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .shadow(
                4.dp,
                RoundedCornerShape(8.dp)
            )

    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    expenseColor
                )
                .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
            Text(expenseHomeModel.expenseDate, color = Color.White)
            Text(expenseHomeModel.expenseState, color = Color.White)
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(expenseColor, RoundedCornerShape(4.dp))
                        .padding(4.dp)
                ) {
                    Icon(
                        painter = painterResource(expenseHomeModel.icon),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    expenseHomeModel.expenseName,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Text(
                expenseHomeModel.amount,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true, apiLevel = 34, showSystemUi = true)
@Composable
fun ExpenseHomeBoxPreview() {
    AppTheme {
        ExpenseHomeBox(
            ExpenseHomeModel(
                0,
                "1 ene 2025",
                "Retrasado",
                R.drawable.ic_savings_category,
                Color.Blue,
                "Arriendo",
                "$1,000,000.00"
            )
        )
    }
}