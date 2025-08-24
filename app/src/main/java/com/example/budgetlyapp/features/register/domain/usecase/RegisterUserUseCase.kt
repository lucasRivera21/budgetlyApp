package com.example.budgetlyapp.features.register.domain.usecase

import com.example.budgetlyapp.common.dataStore.DataStoreTask
import com.example.budgetlyapp.common.dataStore.EmailKey
import com.example.budgetlyapp.common.dataStore.IncomeValueKey
import com.example.budgetlyapp.common.dataStore.UserLastNameKey
import com.example.budgetlyapp.common.dataStore.UserNameKey
import com.example.budgetlyapp.features.register.data.repository.RegisterTask
import com.example.budgetlyapp.features.register.domain.model.RegisterUserModel
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val registerTask: RegisterTask,
    private val dataStore: DataStoreTask
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        registerUser: RegisterUserModel
    ): Result<Unit> {
        val resultNewUser = registerUserAccount(email, password)
        if (resultNewUser.isFailure) {
            return Result.failure(resultNewUser.exceptionOrNull()!!)
        }

        val uuid = resultNewUser.getOrNull()!!
        val resultUserInfo = registerUserInfo(registerUser, uuid)
        if (resultUserInfo.isFailure) {
            return Result.failure(resultUserInfo.exceptionOrNull()!!)
        }

        dataStore.setString(EmailKey.key, email)
        dataStore.setString(UserNameKey.key, registerUser.name!!)
        dataStore.setString(UserLastNameKey.key, registerUser.lastName!!)
        dataStore.setDouble(IncomeValueKey.key, registerUser.incomeValue!!.toDouble())

        return Result.success(Unit)
    }

    private suspend fun registerUserAccount(email: String, password: String): Result<String> {
        return registerTask.registerUser(email, password)
    }

    private suspend fun registerUserInfo(
        registerUser: RegisterUserModel,
        uuid: String
    ): Result<Unit> {
        return registerTask.registerUserInfo(registerUser, uuid)
    }
}