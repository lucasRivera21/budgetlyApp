package com.example.budgetlyapp.di

import com.example.budgetlyapp.features.splash.data.SplashRepository
import com.example.budgetlyapp.features.splash.data.SplashTask
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SplashModule {
    @Binds
    abstract fun bindSplashTask(splashRepository: SplashRepository): SplashTask
}