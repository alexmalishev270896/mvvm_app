package com.alex_malishev.data_layer.injection

import android.app.Application
import com.alex_malishev.data_layer.utils.*
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object NetModule {
    const val HOST_URL = "http://newsapi.org"
    const val API_URL = "$HOST_URL/v2/"

    @Provides
    @Singleton
    fun provideBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
    }

    @Provides
    fun provideServiceGenerator(
        okBuilder: OkHttpClient.Builder, retrofit: Retrofit.Builder,
        authenticationInterceptor: AuthenticationInterceptor,
        connectionInterceptor: ConnectionInterceptor,
        cacheInterceptor: CacheInterceptor,
        offlineCacheInterceptor: OfflineCacheInterceptor,
        networkCache: NetworkCache
    ): ServiceGenerator {
        return ServiceGenerator(
            okBuilder,
            retrofit,
            authenticationInterceptor,
            connectionInterceptor,
            cacheInterceptor,
            offlineCacheInterceptor,
            networkCache
        )
    }

    @Provides
    fun provideConnectionInterceptor(): ConnectionInterceptor {
        return ConnectionInterceptor()
    }

    @Provides
    fun provideAuthenticator(): AuthenticationInterceptor {
        return AuthenticationInterceptor()
    }

    @Provides
    fun provideCacheInterceptor(): CacheInterceptor {
        return CacheInterceptor()
    }

    @Provides
    fun provideOfflineCacheInterceptor(): OfflineCacheInterceptor {
        return OfflineCacheInterceptor()
    }

    @Provides
    fun provideCache(application: Application): NetworkCache {
        return NetworkCache(application)
    }
}