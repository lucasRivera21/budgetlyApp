package com.example.budgetlyapp.present.register.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.budgetlyapp.R
import com.example.budgetlyapp.present.components.CustomTextField
import com.example.budgetlyapp.present.register.RegisterViewModel

@Composable
fun AccountInfoScreen(
    email: String,
    password: String,
    confirmPassword: String,
    registerViewModel: RegisterViewModel
) {
    FormRegister(stringResource(R.string.register_user_info_title)) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            CustomTextField(
                textLabel = stringResource(R.string.lgoin_email_input),
                textValue = email,
                onValueChange = {
                    registerViewModel.onChangeEmail(it)
                }
            )

            CustomTextField(
                textLabel = stringResource(R.string.lgoin_password_input),
                textValue = password,
                onValueChange = {
                    registerViewModel.onChangePassword(it)
                },
                isPasswordField = true
            )

            CustomTextField(
                textLabel = stringResource(R.string.register_repeat_password),
                textValue = confirmPassword,
                onValueChange = {
                    registerViewModel.onChangeConfirmPassword(it)
                },
                isPasswordField = true
            )
        }
    }
}