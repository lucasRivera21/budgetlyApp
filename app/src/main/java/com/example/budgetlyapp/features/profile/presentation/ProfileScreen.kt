package com.example.budgetlyapp.features.profile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.getString
import com.example.budgetlyapp.R
import com.example.budgetlyapp.features.profile.domain.models.ChangePassword
import com.example.budgetlyapp.features.profile.domain.models.ElementProfileModel
import com.example.budgetlyapp.features.profile.domain.models.Logout
import com.example.budgetlyapp.features.profile.domain.models.PersonalData
import com.example.budgetlyapp.features.profile.presentation.components.ElementProfileComponent
import com.example.budgetlyapp.features.profile.presentation.components.HeaderProfileComponent
import com.example.budgetlyapp.ui.theme.AppTheme

@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    val elementProfileList = listOf(
        ElementProfileModel(
            PersonalData.id,
            R.drawable.ic_personal_data,
            getString(context, R.string.profile_personal_data)
        ),
        ElementProfileModel(
            ChangePassword.id,
            R.drawable.ic_shield,
            getString(context, R.string.profile_change_password)
        ),
        ElementProfileModel(
            Logout.id,
            R.drawable.ic_logout,
            getString(context, R.string.profile_logout),
            null
        ),
    )
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(rememberScrollState())
    ) {
        HeaderProfileComponent("Lucas Rivera", "correo@correo.com")

        elementProfileList.forEach {
            ElementProfileComponent(it) {

            }
        }
    }
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun ProfileScreenPreview() {
    AppTheme {
        ProfileScreen()
    }
}