package com.lucasdev.budgetlyapp.di

import com.lucasdev.budgetlyapp.features.profile.data.EditPersonalDataRepository
import com.lucasdev.budgetlyapp.features.profile.data.EditPersonalDataTask
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class EditPersonalDataModule {
    @Binds
    abstract fun bindEditPersonalDataTask(
        editPersonalDataRepository: EditPersonalDataRepository
    ): EditPersonalDataTask

}