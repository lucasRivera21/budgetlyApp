package com.example.budgetlyapp.features.login.data

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface LoginTask {
    suspend fun loginUser(email: String, password: String): Result<Unit>
}

class LoginRepository @Inject constructor(private val auth: FirebaseAuth) : LoginTask {
    override suspend fun loginUser(email: String, password: String): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}