package com.alex_malishev.data_layer.repository

import com.alex_malishev.data_layer.local.INewsLocalSource
import com.alex_malishev.data_layer.mapper.toDomainList
import com.alex_malishev.data_layer.remote.INewsRemoteSource
import com.alex_malishev.domain_layer.model.News
import com.alex_malishev.domain_layer.repository.news.INewsRepository
import io.reactivex.Single
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsRemoteSource: INewsRemoteSource,
    private val newsLocalSource: INewsLocalSource
) : INewsRepository {


    override fun getRecentNewsList(): Single<List<News>> {
        return newsRemoteSource.topHeadlines()
            .map { listResponse ->
                listResponse
                    .toDomainList()
                    .distinctBy { it.title }
            }
            .doOnError { it.printStackTrace() }
    }

    override fun getNewsBy(query: String): Single<List<News>> {
        return newsRemoteSource.everything(query)
            .map { listResponse ->
                listResponse
                    .toDomainList()
                    .distinctBy { it.title }
            }
            .doOnError { it.printStackTrace() }
    }
}