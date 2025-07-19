package com.example.budgetlyapp.common.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.budgetlyapp.R
import com.example.budgetlyapp.ui.theme.AppTheme

@Composable
fun CustomTextField(
    textLabel: String,
    textValue: TextFieldValue,
    keyBoardType: KeyboardType = KeyboardType.Text,
    onValueChange: (TextFieldValue) -> Unit,
    isPasswordField: Boolean = false,
    modifier: Modifier = Modifier
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    val icon =
        if (isPasswordVisible) painterResource(id = R.drawable.ic_visibility) else painterResource(
            id = R.drawable.ic_visibility_off
        )

    OutlinedTextField(
        value = textValue,
        onValueChange = { onValueChange(it) },
        label = { Text(textLabel) },
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyBoardType),
        visualTransformation = if (!isPasswordVisible && isPasswordField) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            if (isPasswordField) {
                Icon(
                    painter = icon,
                    contentDescription = "Visibility",
                    modifier = Modifier.clickable { isPasswordVisible = !isPasswordVisible }
                )
            }
        }
    )
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun CustomTextFieldPreview() {
    AppTheme {
        CustomTextField(
            textLabel = "Email",
            textValue = TextFieldValue("teste"),
            onValueChange = {}
        )
    }
}