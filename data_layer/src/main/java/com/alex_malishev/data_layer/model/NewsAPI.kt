package com.alex_malishev.data_layer.model

sealed class NewsAPI {

    data class NewsListResponse(
        val status: String,
        val articles: List<NewsResponse>
    ): NewsAPI()

    data class NewsResponse(
        val source: NewsSourceResponse?,
        val title: String,
        val publishedAt: String,
        val author: String? = null,
        val description: String? = null,
        val url: String? = null,
        val urlToImage: String? = null,
        val content: String? = null
    ): NewsAPI()

    data class NewsSourceResponse(
        val id: String?,
        val name: String?,
        val description: String?,
        val url: String?,
        val category: String?,
        val language: String?,
        val country: String?
    ): NewsAPI()
}