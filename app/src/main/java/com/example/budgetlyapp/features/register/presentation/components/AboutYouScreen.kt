package com.example.budgetlyapp.features.register.presentation.components

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
import androidx.compose.ui.unit.dp
import com.example.budgetlyapp.R
import com.example.budgetlyapp.common.presentation.components.CustomTextField
import com.example.budgetlyapp.features.register.presentation.RegisterViewModel

@Composable
fun AboutYouScreen(
    name: String,
    lastName: String,
    dayBirth: String,
    monthBirth: String,
    yearBirth: String,
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

            Spacer(Modifier.size(12.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = stringResource(R.string.register_birthday_input),
                )
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    DatePicker(dayBirth, monthBirth, yearBirth, registerViewModel)
                }
            }
        }
    }
}