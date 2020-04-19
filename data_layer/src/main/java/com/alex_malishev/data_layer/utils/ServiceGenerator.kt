package com.alex_malishev.data_layer.utils

import com.alex_malishev.data_layer.injection.NetModule
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * This class is singleton. It creates all remote API services
 */
class ServiceGenerator @Inject constructor(
    private val httpClient: OkHttpClient.Builder,
    private val builder: Retrofit.Builder,
    authenticationInterceptor: AuthenticationInterceptor,
    connectionInterceptor: ConnectionInterceptor,
    cacheInterceptor: CacheInterceptor,
    offlineCacheInterceptor: OfflineCacheInterceptor,
    networkCache: NetworkCache
) {

    init {
        httpClient.addInterceptor(HttpLoggingInterceptor()
            .also { it.level = HttpLoggingInterceptor.Level.BODY })
        httpClient.cache(networkCache.getCache())
        httpClient.addInterceptor(authenticationInterceptor)
        httpClient.addNetworkInterceptor(cacheInterceptor)
        httpClient.addInterceptor(connectionInterceptor)
        httpClient.addInterceptor(offlineCacheInterceptor)
    }

    fun <S> createService(serviceClass: Class<S>): S {
        builder.client(
            httpClient
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build()
        )
        val retrofit = builder.baseUrl(NetModule.API_URL).build()
        return retrofit.create(serviceClass)
    }
}