package com.lucasdev.budgetlyapp.features.login.domain

import com.lucasdev.budgetlyapp.features.login.data.LoginTask
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(private val loginRepository: LoginTask) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return loginRepository.loginUser(email, password)
    }
}