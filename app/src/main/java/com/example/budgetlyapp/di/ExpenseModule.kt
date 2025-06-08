package com.example.budgetlyapp.di

import com.example.budgetlyapp.features.expense.data.repository.CreateExpenseRepository
import com.example.budgetlyapp.features.expense.data.repository.CreateExpenseTask
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ExpenseModule {

    @Binds
    abstract fun bindExpenseRepository(expenseRepository: CreateExpenseRepository): CreateExpenseTask

}