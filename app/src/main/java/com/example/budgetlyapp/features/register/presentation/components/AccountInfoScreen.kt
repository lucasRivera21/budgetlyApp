package com.example.budgetlyapp.features.register.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.budgetlyapp.R
import com.example.budgetlyapp.common.presentation.components.CustomTextField
import com.example.budgetlyapp.features.register.presentation.RegisterViewModel

@Composable
fun AccountInfoScreen(
    email: TextFieldValue,
    password: TextFieldValue,
    confirmPassword: TextFieldValue,
    registerViewModel: RegisterViewModel
) {
    FormRegister(stringResource(R.string.register_user_info_title)) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            CustomTextField(
                textLabel = stringResource(R.string.lgoin_email_input),
                textValue = email,
                keyBoardType = KeyboardType.Email,
                onValueChange = {
                    registerViewModel.onChangeEmail(it)
                }
            )

            CustomTextField(
                textLabel = stringResource(R.string.lgoin_password_input),
                textValue = password,
                keyBoardType = KeyboardType.Password,
                onValueChange = {
                    registerViewModel.onChangePassword(it)
                },
                isPasswordField = true
            )

            CustomTextField(
                textLabel = stringResource(R.string.register_repeat_password),
                textValue = confirmPassword,
                keyBoardType = KeyboardType.Password,
                onValueChange = {
                    registerViewModel.onChangeConfirmPassword(it)
                },
                isPasswordField = true
            )
        }
    }
}