package com.example.budgetlyapp.features.expense.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun DayPayContainer() {
    var number by remember { mutableStateOf("") }
    var hasDay by remember { mutableStateOf(true) }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Column(verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.fillMaxWidth()) {
        Text(
            "¿En que dia tienes que pagar el gasto?",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Medium
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    "Cada",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                BasicTextField(
                    value = number,
                    onValueChange = {
                        number = if (it.isEmpty() || it.toInt() in 1..31) {
                            it
                        } else {
                            "31"
                        }
                    },
                    modifier = Modifier
                        .border(
                            width = if (!hasDay) 1.dp else 3.dp,
                            color = if (!hasDay) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .widthIn(max = 44.dp)
                        .padding(vertical = 10.dp, horizontal = 8.dp)
                        .align(Alignment.CenterVertically)
                        .focusRequester(focusRequester)
                        .onFocusChanged {
                            hasDay = true
                        },
                    textStyle = TextStyle(
                        color = if (!hasDay) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )

                Text(
                    "del mes",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Text(
                "O",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Box(
                modifier = Modifier
                    .border(
                        if (!hasDay) 3.dp else 1.dp,
                        if (!hasDay) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        focusManager.clearFocus()
                        hasDay = false
                    }
                    .padding(vertical = 8.dp, horizontal = 16.dp)

            ) {
                Text(
                    "No tiene dia exacto",
                    style = MaterialTheme.typography.labelLarge,
                    color = if (!hasDay) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}