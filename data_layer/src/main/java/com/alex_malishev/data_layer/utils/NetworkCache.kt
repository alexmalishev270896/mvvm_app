package com.alex_malishev.data_layer.utils

import android.app.Application
import okhttp3.Cache
import java.io.File
import javax.inject.Inject

class NetworkCache @Inject constructor(private val context: Application) {

    companion object{
        const val CACHE_SIZE = 10L * 1024 * 1024
    }

    fun getCache() = Cache(File( context.cacheDir, "apiCache"), CACHE_SIZE)

}