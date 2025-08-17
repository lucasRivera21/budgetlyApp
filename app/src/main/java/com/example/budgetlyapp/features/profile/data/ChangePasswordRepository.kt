package com.example.budgetlyapp.features.profile.data

import android.content.Context
import androidx.core.content.ContextCompat.getString
import com.example.budgetlyapp.R
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface ChangePasswordTask {
    suspend fun validateCurrentPassword(currentPassword: String): Result<String>
    suspend fun changePassword(newPassword: String): Result<String>
}

class ChangePasswordRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val auth: FirebaseAuth
) : ChangePasswordTask {
    override suspend fun validateCurrentPassword(currentPassword: String): Result<String> {
        val userEmail =
            auth.currentUser?.email ?: return Result.failure(
                Throwable(
                    getString(
                        context,
                        R.string.error_user_not_found
                    )
                )
            )

        val credential = EmailAuthProvider.getCredential(userEmail, currentPassword)

        return try {
            auth.currentUser!!.reauthenticate(credential).await()
            Result.success("")
        } catch (e: Exception) {
            Result.failure(Throwable(getString(context, R.string.error_wrong_password)))
        }
    }

    override suspend fun changePassword(newPassword: String): Result<String> {
        val user = auth.currentUser ?: return Result.failure(
            Throwable(
                getString(
                    context,
                    R.string.error_user_not_found
                )
            )
        )

        try {
            user.updatePassword(newPassword).await()
            return Result.success("")
        } catch (e: Exception) {
            return Result.failure(Throwable(getString(context, R.string.error_error_unknown)))
        }
    }
}