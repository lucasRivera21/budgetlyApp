package com.example.budgetlyapp.present.register.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.budgetlyapp.R
import com.example.budgetlyapp.present.components.CustomTextField
import com.example.budgetlyapp.ui.theme.AppTheme

@Composable
fun AboutYouScreen() {
    Column {
        FormRegister(
            registerTitle = stringResource(R.string.register_about_you_title)
        ) {
            CustomTextField(
                textLabel = stringResource(R.string.register_first_name_input),
                textValue = "",
                onValueChange = {}
            )

            CustomTextField(
                textLabel = stringResource(R.string.register_last_name_input),
                textValue = "",
                onValueChange = {}
            )

            Spacer(Modifier.size(12.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = stringResource(R.string.register_birthday_input),
                )
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    DatePicker()
                }
            }
        }
    }
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun AboutYouScreenPreview() {
    AppTheme {
        AboutYouScreen()
    }
}