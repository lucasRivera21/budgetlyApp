package com.example.budgetlyapp.features.profile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.budgetlyapp.R
import com.example.budgetlyapp.common.presentation.components.CustomTextField

@Composable
fun ChangePasswordScreen(navController: NavHostController) {
    var currentPassword by remember { mutableStateOf(TextFieldValue()) }
    var newPassword by remember { mutableStateOf(TextFieldValue()) }
    var confirmPassword by remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(48.dp)
    ) {
        //Header
        Row(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = {
                navController.popBackStack()
            }, modifier = Modifier) {
                Icon(
                    painterResource(R.drawable.ic_arrow_back),
                    contentDescription = "arrow back",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(24.dp)
                )
            }

            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    stringResource(R.string.profile_change_password),
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center
                )
            }
        }

        //Content
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(32.dp)) {
            CustomTextField(
                textLabel = stringResource(R.string.profile_current_password),
                textValue = currentPassword,
                keyBoardType = KeyboardType.Password,
                isPasswordField = true,
                onValueChange = {
                    currentPassword = it
                })

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

            CustomTextField(
                textLabel = stringResource(R.string.profile_new_password),
                textValue = newPassword,
                keyBoardType = KeyboardType.Password,
                isPasswordField = true,
                onValueChange = {
                    newPassword = it
                })

            CustomTextField(
                textLabel = stringResource(R.string.profile_repeat_new_password),
                textValue = confirmPassword,
                keyBoardType = KeyboardType.Password,
                isPasswordField = true,
                onValueChange = {
                    confirmPassword = it
                }
            )
        }

        //Footer
        Button(
            onClick = {}, modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(stringResource(R.string.profile_save))
        }

    }
}