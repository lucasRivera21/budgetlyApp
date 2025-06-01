package com.example.budgetlyapp.di

import com.example.budgetlyapp.features.login.data.LoginRepository
import com.example.budgetlyapp.features.login.data.LoginTask
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LoginModule {
    @Binds
    abstract fun bindLoginRepository(loginRepository: LoginRepository): LoginTask
}