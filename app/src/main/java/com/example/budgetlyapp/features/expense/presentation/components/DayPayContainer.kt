package com.example.budgetlyapp.features.expense.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.budgetlyapp.R
import com.example.budgetlyapp.features.expense.presentation.viewModels.CreateExpenseViewModel

@Composable
fun DayPayContainer(dayPayString: String, hasDayPay: Boolean, viewModel: CreateExpenseViewModel) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Column(verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.fillMaxWidth()) {
        Text(
            stringResource(R.string.create_expense_day_pay_title),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Medium
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    stringResource(R.string.create_expense_each),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                BasicTextField(
                    value = dayPayString,
                    onValueChange = {
                        viewModel.onChangeDayPayString(it)
                    },
                    modifier = Modifier
                        .border(
                            width = if (!hasDayPay) 1.dp else 3.dp,
                            color = if (!hasDayPay) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .widthIn(max = 44.dp)
                        .padding(vertical = 10.dp, horizontal = 8.dp)
                        .align(Alignment.CenterVertically)
                        .focusRequester(focusRequester)
                        .onFocusChanged {
                            viewModel.onChangeHasDayPay(true)
                        },
                    textStyle = TextStyle(
                        color = if (!hasDayPay) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )

                Text(
                    stringResource(R.string.create_expense_of_the_month),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Text(
                stringResource(R.string.create_expense_or),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Box(
                modifier = Modifier
                    .border(
                        if (!hasDayPay) 3.dp else 1.dp,
                        if (!hasDayPay) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        focusManager.clearFocus()
                        viewModel.onChangeHasDayPay(false)
                    }
                    .padding(vertical = 8.dp, horizontal = 16.dp)

            ) {
                Text(
                    stringResource(R.string.create_expense_without_day),
                    style = MaterialTheme.typography.labelLarge,
                    color = if (!hasDayPay) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}