package com.example.budgetlyapp.di

import com.example.budgetlyapp.features.register.data.repository.RegisterRepository
import com.example.budgetlyapp.features.register.data.repository.RegisterTask
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RegisterModule {
    @Binds
    abstract fun bindRegisterTask(
        registerRepository: RegisterRepository
    ): RegisterTask
}