package com.alex_malishev.mvvm_app.injection

import android.content.Context
import com.alex_malishev.mvvm_app.MvvmApplication
import com.alex_malishev.mvvm_app.injection.qualifiers.ApplicationContext
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    @ApplicationContext
    abstract fun provideApplicationContext(application: MvvmApplication): Context
}