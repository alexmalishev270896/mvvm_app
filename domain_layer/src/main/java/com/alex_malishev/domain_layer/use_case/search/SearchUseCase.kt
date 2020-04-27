package com.alex_malishev.domain_layer.use_case.search

import com.alex_malishev.domain_layer.model.News
import com.alex_malishev.domain_layer.repository.news.INewsRepository
import io.reactivex.Single
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val newsRepository: INewsRepository) :
    ISearchUseCase {

    override fun invoke(query: String): Single<List<News>> {
        return newsRepository.getNewsBy(query)
    }
}