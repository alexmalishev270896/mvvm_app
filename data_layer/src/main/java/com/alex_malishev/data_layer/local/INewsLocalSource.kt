package com.alex_malishev.data_layer.local

import com.alex_malishev.domain_layer.model.News
import io.reactivex.Completable
import io.reactivex.Single

interface INewsLocalSource {
    fun getNews(): Single<List<News>>

    fun saveNews(news: News): Single<News>

    fun deleteNews(news: News): Single<News>
}