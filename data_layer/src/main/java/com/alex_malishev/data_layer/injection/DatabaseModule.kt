package com.alex_malishev.data_layer.injection

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.alex_malishev.data_layer.db.AppDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {
    const val DATABASE_VERSION = 1
    @Provides
    fun provideDB(context: Application): AppDb {
        return Room
            .databaseBuilder(context, AppDb::class.java, "app.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}