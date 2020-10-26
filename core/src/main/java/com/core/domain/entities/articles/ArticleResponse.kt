package com.core.domain.entities.articles

data class ArticleResponse (
        val status : String,
        val totalResults : Int,
        val message : String,
        val articles : ArrayList<Article>
)