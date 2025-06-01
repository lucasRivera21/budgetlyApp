package com.example.budgetlyapp.features.login.domain

import com.example.budgetlyapp.features.login.data.LoginTask
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(private val loginRepository: LoginTask) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return loginRepository.loginUser(email, password)
    }
}