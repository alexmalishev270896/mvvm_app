package com.alex_malishev.data_layer.remote

import com.alex_malishev.data_layer.model.NewsAPI
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface INewsRemoteSource {

    @GET("top-headlines?country=us")
    fun topHeadlines(): Single<NewsAPI.NewsListResponse>

    @GET(value = "everything")
    fun everything(@Query(value = "q") query: String): Single<NewsAPI.NewsListResponse>
}