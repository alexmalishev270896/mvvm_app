package com.alex_malishev.domain_layer.model

data class News(val title: String,
                val date: String,
                val author: String? = null,
                val description: String? = null,
                val url: String? = null,
                val imageUrl: String? = null,
                val content: String? = null,
                val source: NewsSource? = null,
                val isFavourite: Boolean = false
)