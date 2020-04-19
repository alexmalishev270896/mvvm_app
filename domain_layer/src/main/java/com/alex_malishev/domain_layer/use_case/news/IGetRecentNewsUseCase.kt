package com.alex_malishev.domain_layer.use_case.news

import com.alex_malishev.domain_layer.model.News
import io.reactivex.Single

interface IGetRecentNewsUseCase {
    operator fun invoke(): Single<List<News>>
}