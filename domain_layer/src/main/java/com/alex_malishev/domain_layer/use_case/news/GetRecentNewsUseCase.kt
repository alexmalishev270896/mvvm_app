package com.alex_malishev.domain_layer.use_case.news

import com.alex_malishev.domain_layer.model.News
import com.alex_malishev.domain_layer.repository.news.INewsRepository
import io.reactivex.Single
import javax.inject.Inject

class GetRecentNewsUseCase @Inject constructor(private val newsRepository: INewsRepository) :
    IGetRecentNewsUseCase {

    override fun invoke(): Single<List<News>> {
        return newsRepository.getRecentNewsList()
    }
}