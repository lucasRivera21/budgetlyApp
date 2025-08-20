package com.example.budgetlyapp.features.profile.presentation.viewModel

import android.content.Context
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.budgetlyapp.R
import com.example.budgetlyapp.features.profile.domain.models.ChangePassword
import com.example.budgetlyapp.features.profile.domain.models.ElementProfileModel
import com.example.budgetlyapp.features.profile.domain.models.Logout
import com.example.budgetlyapp.features.profile.domain.models.PersonalData
import com.example.budgetlyapp.features.profile.domain.useCase.GetUserInfoUseCase
import com.example.budgetlyapp.navigation.ChangePasswordScreen
import com.example.budgetlyapp.navigation.EditPersonalDataScreen
import com.example.budgetlyapp.navigation.LoginScreen
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val auth: FirebaseAuth,
    private val getUserInfoUseCase: GetUserInfoUseCase

) : ViewModel() {
    val elementProfileList = listOf(
        ElementProfileModel(
            PersonalData.id,
            R.drawable.ic_personal_data,
            getString(context, R.string.profile_edit_personal_data)
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

    private val _userName = MutableStateFlow("")
    val userName: MutableStateFlow<String> = _userName

    private val _email = MutableStateFlow("")
    val email: MutableStateFlow<String> = _email

    private val _showLogoutDialog = MutableStateFlow(false)
    val showLogoutDialog: MutableStateFlow<Boolean> = _showLogoutDialog

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val userInfoResult = getUserInfoUseCase()
            if (userInfoResult.isSuccess) {
                val profileModel = userInfoResult.getOrNull()!!

                _userName.value = "${profileModel.name} ${profileModel.lastName}"
                _email.value = profileModel.email
            }
        }
    }

    fun onClickElementProfile(id: String, navController: NavController) {
        when (id) {
            PersonalData.id -> {
                navController.navigate(EditPersonalDataScreen.route)
            }

            ChangePassword.id -> {
                navController.navigate(ChangePasswordScreen.route)
            }

            Logout.id -> {
                showLogoutDialog()
            }

            else -> Unit
        }
    }

    fun onDismissLogoutDialog() {
        _showLogoutDialog.value = false
    }

    private fun showLogoutDialog() {
        _showLogoutDialog.value = true
    }

    fun logout(navController: NavController) {
        auth.signOut()
        onDismissLogoutDialog()
        navController.navigate(LoginScreen.route) {
            popUpTo(LoginScreen.route) {
                inclusive = true
            }
        }
    }
}