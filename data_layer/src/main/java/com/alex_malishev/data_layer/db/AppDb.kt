package com.alex_malishev.data_layer.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alex_malishev.data_layer.injection.DatabaseModule
import com.alex_malishev.data_layer.model.NewsDb

@Database(entities = [NewsDb.NewsEntity::class], version = DatabaseModule.DATABASE_VERSION,
    exportSchema = false)
abstract class AppDb : RoomDatabase() {

    abstract fun newsDao(): NewsDao
}