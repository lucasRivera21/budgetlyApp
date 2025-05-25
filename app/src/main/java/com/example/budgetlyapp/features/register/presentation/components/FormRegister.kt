package com.example.budgetlyapp.features.register.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.budgetlyapp.ui.theme.AppTheme

@Composable
fun FormRegister(registerTitle: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            registerTitle,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(Modifier.size(64.dp))

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            content()
        }
    }
}

@Preview(showSystemUi = true, showBackground = true, apiLevel = 34)
@Composable
fun FormRegisterPreview() {
    AppTheme {
        FormRegister("Primero queremos saber quién eres", {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Nombre") }
            )
        })
    }
}