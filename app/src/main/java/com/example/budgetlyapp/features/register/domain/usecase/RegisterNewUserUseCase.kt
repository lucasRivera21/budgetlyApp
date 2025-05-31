package com.example.budgetlyapp.features.register.domain.usecase

import android.util.Log
import com.example.budgetlyapp.features.register.data.repository.RegisterTask
import javax.inject.Inject

class RegisterNewUserUseCase @Inject constructor(private val registerTask: RegisterTask) {
    suspend operator fun invoke(email: String, password: String): Result<String> {
        Log.d("RegisterViewModel", "email: $email, password: $password")
        return registerTask.registerUser(email, password)
    }
}