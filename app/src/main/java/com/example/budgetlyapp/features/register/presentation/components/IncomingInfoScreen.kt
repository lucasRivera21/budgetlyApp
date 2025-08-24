package com.example.budgetlyapp.features.register.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.budgetlyapp.R
import com.example.budgetlyapp.common.presentation.components.CustomTextField
import com.example.budgetlyapp.features.register.presentation.RegisterViewModel

@Composable
fun IncomingInfoScreen(
    incomeValue: TextFieldValue,
    moneyType: String,
    registerViewModel: RegisterViewModel
) {
    var expanded by remember { mutableStateOf(false) }
    val options = registerViewModel.returnMoneyTypeList()
    val animationRotate by animateFloatAsState(
        targetValue = if (expanded) -180f else 0f,
        label = "Rotate"
    )

    FormRegister(stringResource(R.string.register_budget_info_title)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.fillMaxWidth()
        ) {
            CustomTextField(
                textLabel = stringResource(R.string.register_income_input),
                textValue = incomeValue,
                keyBoardType = KeyboardType.Decimal,
                onValueChange = {
                    registerViewModel.onIncomeValueChange(it)
                },
                modifier = Modifier.weight(1f)
            )

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .height(56.dp)
                        .clickable { expanded = true }
                        .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(4.dp))
                        .padding(8.dp)

                ) {
                    Text(moneyType)
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "arrow down icon",
                        modifier = Modifier
                            .size(24.dp)
                            .rotate(animationRotate)
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    options.forEach {
                        DropdownMenuItem(
                            text = { Text(it) },
                            onClick = {
                                registerViewModel.onMoneyTypeChange(it)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}