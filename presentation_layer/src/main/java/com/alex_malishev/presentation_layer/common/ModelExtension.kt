package com.alex_malishev.presentation_layer.common

import com.alex_malishev.domain_layer.model.News
import com.alex_malishev.domain_layer.model.NewsSource
import com.alex_malishev.presentation_layer.model.NewsParcelable
import com.alex_malishev.presentation_layer.model.NewsSourceParcelable


fun News.toParcelable(): NewsParcelable {
    return NewsParcelable(title, date, author, description, url, imageUrl, content, source?.toParcelable())
}

fun NewsParcelable.toDomain(): News {
    return News(title, date, author, description, url, imageUrl)
}

fun NewsSource.toParcelable(): NewsSourceParcelable{
    return NewsSourceParcelable(id, name, description, url, category, language, country)
}

fun NewsSourceParcelable.toDomain(): NewsSource{
    return NewsSource(id, name, description, url, category, language, country)
}