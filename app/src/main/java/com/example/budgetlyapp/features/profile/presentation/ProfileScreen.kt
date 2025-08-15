package com.example.budgetlyapp.features.profile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.budgetlyapp.R
import com.example.budgetlyapp.features.profile.presentation.viewModel.ProfileViewModel
import com.example.budgetlyapp.features.profile.presentation.components.ElementProfileComponent
import com.example.budgetlyapp.features.profile.presentation.components.HeaderProfileComponent

@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val elementProfileList = profileViewModel.elementProfileList
    val showLogoutDialog by profileViewModel.showLogoutDialog.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(rememberScrollState())
    ) {
        HeaderProfileComponent("Lucas Rivera", "correo@correo.com")

        elementProfileList.forEach {
            ElementProfileComponent(it) { id ->
                profileViewModel.onClickElementProfile(id)
            }
        }
    }

    if (showLogoutDialog) {
        AlertDialog(
            title = { Text(stringResource(R.string.profile_dialog_title)) },
            text = { Text(stringResource(R.string.profile_dialog_description)) },
            confirmButton = {
                TextButton(onClick = { profileViewModel.logout(navController) }) {
                    Text(text = stringResource(R.string.profile_close_session))
                }
            },
            dismissButton = {
                TextButton(onClick = { profileViewModel.onDismissLogoutDialog() }) {
                    Text(text = stringResource(R.string.dialog_cancel))
                }
            },
            onDismissRequest = {
                profileViewModel.onDismissLogoutDialog()
            }
        )
    }
}