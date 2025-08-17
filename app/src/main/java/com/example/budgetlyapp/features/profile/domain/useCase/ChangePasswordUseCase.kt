package com.example.budgetlyapp.features.profile.domain.useCase

import com.example.budgetlyapp.features.profile.data.ChangePasswordTask
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(private val changePasswordTask: ChangePasswordTask) {
    suspend operator fun invoke(newPassword: String): Result<String> {
        return changePasswordTask.changePassword(newPassword)
    }
}