package com.example.budgetlyapp.present.register.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.budgetlyapp.R
import com.example.budgetlyapp.present.components.CustomTextField
import com.example.budgetlyapp.ui.theme.AppTheme

@Composable
fun AccountInfoScreen() {
    FormRegister(stringResource(R.string.register_user_info_title)) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            CustomTextField(
                textLabel = stringResource(R.string.lgoin_email_input),
                textValue = "",
                onValueChange = {}
            )

            CustomTextField(
                textLabel = stringResource(R.string.lgoin_password_input),
                textValue = "",
                onValueChange = {},
                isPasswordField = true
            )

            CustomTextField(
                textLabel = stringResource(R.string.register_repeat_password),
                textValue = "",
                onValueChange = {},
                isPasswordField = true
            )
        }
    }
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun AccountInfoScreenPreview() {
    AppTheme {
        AccountInfoScreen()
    }
}