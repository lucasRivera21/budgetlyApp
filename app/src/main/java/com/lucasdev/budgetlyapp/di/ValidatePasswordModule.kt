package com.lucasdev.budgetlyapp.di

import com.lucasdev.budgetlyapp.features.profile.data.ChangePasswordRepository
import com.lucasdev.budgetlyapp.features.profile.data.ChangePasswordTask
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ValidatePasswordModule {
    @Binds
    abstract fun bindValidatePasswordTask(changePasswordRepository: ChangePasswordRepository): ChangePasswordTask

}