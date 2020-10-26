package com.apinews.data.mappers

import com.core.domain.entities.articles.Article
import com.core.domain.entities.articles.ArticleResponse

class ArticlesMapper {
    fun responseToArticles(response: ArticleResponse): ArrayList<Article> = response.articles
}