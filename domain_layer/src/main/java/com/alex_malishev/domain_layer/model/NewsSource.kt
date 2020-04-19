package com.alex_malishev.domain_layer.model

data class NewsSource(
    val id: String,
    val name: String,
    val description: String,
    val url: String,
    val category: String,
    val language: String,
    val country: String
) {
    constructor(id: String, name: String) : this(id, name, "", "", "", "", "")
}