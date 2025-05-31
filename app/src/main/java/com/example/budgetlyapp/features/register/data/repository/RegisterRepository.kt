package com.example.budgetlyapp.features.register.data.repository

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface RegisterTask {
    suspend fun registerUser(email: String, password: String): Result<String>
}

class RegisterRepository @Inject constructor(private val auth: FirebaseAuth) : RegisterTask {
    override suspend fun registerUser(email: String, password: String): Result<String> {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            Result.success(auth.currentUser?.uid!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}