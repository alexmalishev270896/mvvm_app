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
        val id: String? = null,
        val name: String? = null,
        val description: String? = null,
        val url: String? = null,
        val category: String? = null,
        val language: String? = null,
        val country: String? = null
    ): NewsAPI()
}