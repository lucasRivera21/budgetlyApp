package com.example.budgetlyapp.di

import com.example.budgetlyapp.features.home.data.HomeRepository
import com.example.budgetlyapp.features.home.data.HomeTask
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeModule {
    @Binds
    abstract fun bindHomeRepository(homeRepository: HomeRepository): HomeTask

}