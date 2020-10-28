package com.core.domain.entities.articles

data class Article (
    val title: String,
    val description: String,
    val url: String,
    var urlToImage: String
)