package com.example.budgetlyapp.features.expense.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.budgetlyapp.R
import com.example.budgetlyapp.common.presentation.components.CustomTextField

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