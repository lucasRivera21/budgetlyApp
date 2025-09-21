package com.lucasdev.budgetlyapp.di

import com.lucasdev.budgetlyapp.alarm.AlarmScheduler
import com.lucasdev.budgetlyapp.alarm.AlarmSchedulerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AlarmModule {
    @Binds
    abstract fun bindAlarmScheduler(alarmSchedulerImpl: AlarmSchedulerImpl): AlarmScheduler
}