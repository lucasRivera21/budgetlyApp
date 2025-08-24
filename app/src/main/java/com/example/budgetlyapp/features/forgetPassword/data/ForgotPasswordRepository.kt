package com.example.budgetlyapp.features.forgetPassword.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "ForgotPasswordRepository"

interface ForgotPasswordTask {
    suspend fun sendChangePassword(email: String): Boolean
}

class ForgotPasswordRepository @Inject constructor(private val auth: FirebaseAuth) :
    ForgotPasswordTask {
    override suspend fun sendChangePassword(email: String): Boolean {
        return try {
            auth.sendPasswordResetEmail(email).await()
            true
        } catch (e: Exception) {
            Log.d(TAG, "sendChangePassword: ${e.message}")
            false
        }
    }
}