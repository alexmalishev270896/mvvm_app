package com.alex_malishev.domain_layer.repository.news

import com.alex_malishev.domain_layer.model.News
import io.reactivex.Completable
import io.reactivex.Single

interface INewsRepository {

    fun getRecentNewsList(): Single<List<News>>

    fun getNewsBy(query: String): Single<List<News>>
}