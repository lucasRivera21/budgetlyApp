package com.example.budgetlyapp.features.forgetPassword.domain

import com.example.budgetlyapp.features.forgetPassword.data.ForgotPasswordTask
import javax.inject.Inject

class SendChangePasswordUseCase @Inject constructor(private val forgotPasswordTask: ForgotPasswordTask) {
    suspend operator fun invoke(email: String): Boolean {
        return forgotPasswordTask.sendChangePassword(email)
    }
}