package com.alex_malishev.data_layer.utils

import com.alex_malishev.data_layer.DataLayer
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

/**
 * This is interceptor that handles authorization logic for each request
 * and handles all response statuses
 */
class AuthenticationInterceptor @Inject constructor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val headers = request
            .headers()
            .newBuilder()
            .add("Authorization", "Bearer ${UserStorage.accessToken}")
            .add("X-Api-Key", DataLayer.API_KEY)
            .build()
        return chain.proceed(request.newBuilder().headers(headers).build())
    }
}