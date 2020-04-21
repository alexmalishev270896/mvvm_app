package com.alex_malishev.data_layer.local

import com.alex_malishev.data_layer.db.NewsDao
import com.alex_malishev.data_layer.mapper.toDbEntity
import com.alex_malishev.data_layer.mapper.toDomain
import com.alex_malishev.domain_layer.model.News
import io.reactivex.Single
import javax.inject.Inject

class NewsLocalSource @Inject constructor(
    private val newsDao: NewsDao
) : INewsLocalSource {

    override fun getNews(): Single<List<News>> {
        return newsDao.getNews()
            .map { list -> list.map { it.toDomain(true) } }
    }

    override fun saveNews(news: News): Single<News> {
        return Single.just(news)
            .map { it.toDbEntity() }
            .flatMapCompletable {
                newsDao.saveNews(it)
            }.toSingle { news.copy(isFavourite =  true) }
    }

    override fun deleteNews(news: News): Single<News> {
        return Single.just(news)
            .map { it.toDbEntity() }
            .flatMapCompletable {
                newsDao.deleteNews(it.title)
            }.toSingle { news.copy(isFavourite = false) }
    }
}