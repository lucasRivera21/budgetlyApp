package com.example.budgetlyapp.features.expense.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.example.budgetlyapp.R

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