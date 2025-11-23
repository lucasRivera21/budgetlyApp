package com.lucasdev.budgetlyapp.features.register.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import com.lucasdev.budgetlyapp.R
import com.lucasdev.budgetlyapp.common.presentation.components.CustomTextField
import com.lucasdev.budgetlyapp.features.register.presentation.RegisterViewModel

@Composable
fun AboutYouScreen(
    name: TextFieldValue,
    lastName: TextFieldValue,
    registerViewModel: RegisterViewModel
) {
    Column {
        FormRegister(
            registerTitle = stringResource(R.string.register_about_you_title)
        ) {
            CustomTextField(
                textLabel = stringResource(R.string.register_first_name_input),
                textValue = name,
                onValueChange = {
                    registerViewModel.onNameChange(it)
                }
            )

            CustomTextField(
                textLabel = stringResource(R.string.register_last_name_input),
                textValue = lastName,
                onValueChange = {
                    registerViewModel.onLastNameChange(it)
                }
            )
        }
    }
}