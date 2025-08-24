package com.example.budgetlyapp.di

import com.example.budgetlyapp.features.forgetPassword.data.ForgotPasswordRepository
import com.example.budgetlyapp.features.forgetPassword.data.ForgotPasswordTask
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ForgotPasswordModule {
    @Binds
    abstract fun bindForgotPasswordRepository(
        forgotPasswordRepository: ForgotPasswordRepository
    ): ForgotPasswordTask

}