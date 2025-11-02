package com.lucasdev.budgetlyapp.features.register.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lucasdev.budgetlyapp.ui.theme.AppTheme

@Composable
fun FormRegister(registerTitle: String, content: @Composable () -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(60.dp)
    ) {
        item {
            Text(
                registerTitle,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                content()
            }
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