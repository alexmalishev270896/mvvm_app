package com.alex_malishev.mvvm_app.injection

import com.alex_malishev.presentation_layer.common.BaseSchedulerProvider
import com.alex_malishev.presentation_layer.common.SchedulerProvider
import dagger.Binds
import dagger.Module

@Module
abstract class SchedulerModule {

    @Binds
    abstract fun provideScheduler(schedulerProvider: SchedulerProvider): BaseSchedulerProvider
}