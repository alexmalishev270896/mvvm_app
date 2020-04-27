package com.alex_malishev.domain_layer.use_case.search

import com.alex_malishev.domain_layer.model.News
import io.reactivex.Single

interface ISearchUseCase {

    operator fun invoke(query: String): Single<List<News>>
}