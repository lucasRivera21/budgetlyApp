package com.lucasdev.budgetlyapp.di

import com.lucasdev.budgetlyapp.features.expense.data.repository.CreateExpenseRepository
import com.lucasdev.budgetlyapp.features.expense.data.repository.CreateExpenseTask
import com.lucasdev.budgetlyapp.features.expense.data.repository.ExpenseRepository
import com.lucasdev.budgetlyapp.features.expense.data.repository.ExpenseTask
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ExpenseModule {

    @Binds
    abstract fun bindCreateExpenseRepository(expenseRepository: CreateExpenseRepository): CreateExpenseTask

    @Binds
    abstract fun bindExpenseRepository(expenseRepository: ExpenseRepository): ExpenseTask

}