package com.example.budgetlyapp.di

import com.example.budgetlyapp.features.profile.data.ProfileRepository
import com.example.budgetlyapp.features.profile.data.ProfileTask
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileModule {
    @Binds
    abstract fun bindProfileRepository(
        profileRepository: ProfileRepository
    ): ProfileTask
}