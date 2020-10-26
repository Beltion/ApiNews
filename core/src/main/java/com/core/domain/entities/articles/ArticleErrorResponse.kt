package com.core.domain.entities.articles

import com.sun.xml.internal.ws.developer.Serialization

data class ArticleErrorResponse(
        val status : String,
        val code : String,
        val message : String,
)