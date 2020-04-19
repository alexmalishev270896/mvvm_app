package com.alex_malishev.data_layer.utils

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.lang.Exception

/**
 * This is connectivity interceptor
 */
class ConnectionInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        try {
            return chain.proceed(request)
        }catch (e: Exception) {
            if (!NetworkStateHolder.isConnected) {
                throw NoConnectivityException()
            } else {
                throw e
            }
        }
    }
}