package com.core.domain.entities.articles

data class ArticleErrorResponse(
        val status : String,
        val code : String,
        val message : String,
)