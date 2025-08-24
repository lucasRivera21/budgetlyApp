package com.example.budgetlyapp.features.login.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.budgetlyapp.R
import com.example.budgetlyapp.navigation.RegisterScreen
import com.example.budgetlyapp.common.presentation.components.CustomTextField
import com.example.budgetlyapp.navigation.ForgotPasswordScreen
import com.example.budgetlyapp.ui.theme.AppTheme

@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel = hiltViewModel()) {
    val email by loginViewModel.email.collectAsState()
    val password by loginViewModel.password.collectAsState()
    val isLoading by loginViewModel.isLoading.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 48.dp, bottom = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.lgoin_title),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.W500,
            textAlign = TextAlign.Center
        )

        BodyInputs(email, password, loginViewModel, navController, isLoading)

        RegisterForgetBox(navController)

        TermsAndConditions()
    }
}

@Composable
fun BodyInputs(
    email: TextFieldValue,
    password: TextFieldValue,
    loginViewModel: LoginViewModel,
    navController: NavController,
    isLoading: Boolean
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        CustomTextField(
            textLabel = stringResource(R.string.lgoin_email_input),
            textValue = email,
            keyBoardType = KeyboardType.Email,
            onValueChange = {
                loginViewModel.onChangeEmail(it)
            }
        )

        CustomTextField(
            textLabel = stringResource(R.string.lgoin_password_input),
            textValue = password,
            keyBoardType = KeyboardType.Password,
            onValueChange = {
                loginViewModel.onChangePassword(it)
            },
            isPasswordField = true
        )

        Button(onClick = {
            loginViewModel.loginUser(navController)
        }, modifier = Modifier.fillMaxWidth()) {
            if (!isLoading) {
                Text(text = stringResource(R.string.lgoin_enter_button))
            } else {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun RegisterForgetBox(navController: NavController) {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row {
            Text(
                stringResource(R.string.lgoin_new_user),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.size(4.dp))
            Text(
                stringResource(R.string.lgoin_register),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelLarge,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    navController.navigate(RegisterScreen.route)
                }
            )
        }

        Text(
            stringResource(R.string.lgoin_forgot_password),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable {
                navController.navigate(ForgotPasswordScreen.route)
            }
        )
    }
}

@Composable
fun TermsAndConditions() {
    Row {
        Text(
            stringResource(R.string.lgoin_accept_terms),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.size(4.dp))
        Text(
            stringResource(R.string.lgoin_terms_and_conditions),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable { }
        )
    }
}

@Preview(showSystemUi = true, showBackground = true, apiLevel = 34)
@Composable
fun LoginScreenPreview() {
    AppTheme {
        LoginScreen(NavController(LocalContext.current))
    }
}