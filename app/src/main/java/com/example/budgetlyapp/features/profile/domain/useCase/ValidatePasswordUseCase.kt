package com.example.budgetlyapp.features.profile.domain.useCase

import android.content.Context
import androidx.core.content.ContextCompat.getString
import com.example.budgetlyapp.R
import com.example.budgetlyapp.common.utils.isValidatePassword
import com.example.budgetlyapp.features.profile.data.ChangePasswordTask
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val changePasswordTask: ChangePasswordTask,
) {
    suspend operator fun invoke(
        currentPassword: String,
        newPassword: String,
        confirmPassword: String
    ): Result<String> {
        if (currentPassword.isEmpty()) return Result.failure(
            Throwable(getString(context, R.string.error_value_empty))
        )

        if (isValidatePassword(currentPassword)) return Result.failure(
            Throwable(getString(context, R.string.error_short_password))
        )

        if (newPassword.isEmpty()) return Result.failure(
            Throwable(getString(context, R.string.error_value_empty))
        )

        if (isValidatePassword(newPassword)) return Result.failure(
            Throwable(getString(context, R.string.error_short_password))
        )

        if (confirmPassword.isEmpty()) return Result.failure(
            Throwable(getString(context, R.string.error_value_empty))
        )

        if (newPassword != confirmPassword) return Result.failure(
            Throwable(getString(context, R.string.error_different_password))
        )

        return changePasswordTask.validateCurrentPassword(currentPassword)
    }
}