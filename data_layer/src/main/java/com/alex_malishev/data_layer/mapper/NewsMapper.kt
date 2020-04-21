package com.alex_malishev.data_layer.mapper

import com.alex_malishev.data_layer.model.NewsAPI
import com.alex_malishev.data_layer.model.NewsDb
import com.alex_malishev.domain_layer.model.News
import com.alex_malishev.domain_layer.model.NewsSource

fun NewsAPI.NewsListResponse.toDomainList(): List<News> {
    return articles.map { it.toDomain() }
}

fun NewsAPI.NewsResponse.toDomain(): News {
    return News(title, publishedAt, author, description, url, urlToImage, content, source?.toDomain())
}

fun NewsAPI.NewsSourceResponse.toDomain(): NewsSource {
    return NewsSource(
        id.extractValue(),
        name.extractValue(),
        description.extractValue(),
        url.extractValue(),
        category.extractValue(),
        language.extractValue(),
        country.extractValue()
    )
}

fun News.toDbEntity(): NewsDb.NewsEntity {
    return NewsDb.NewsEntity(title, date, author, description, url, imageUrl,
        sourceId =  source?.id, sourceName = source?.name)
}

fun NewsDb.NewsEntity.toDomain(isFavourite: Boolean = false): News {
    return News(
        title, date, author, description, url, imageUrl, content,
        NewsSource(sourceId.extractValue(), sourceName.extractValue()), isFavourite
    )
}

private fun String?.extractValue(): String {
    return this ?: ""
}