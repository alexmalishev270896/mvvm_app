package com.alex_malishev.data_layer.db

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DaoProvider @Inject constructor(private val appDb: AppDb) {

    fun newsDao(): NewsDao = appDb.newsDao()
}