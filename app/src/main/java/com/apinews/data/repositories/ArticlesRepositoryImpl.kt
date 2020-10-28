package com.apinews.data.repositories

import com.apinews.data.mappers.ArticlesMapper
import com.apinews.framework.articles.ArticlesApiDataSource
import com.core.data.articles.ArticlesRepository
import com.core.domain.callbacks.FailureCallback
import com.core.domain.callbacks.SuccessCallback
import com.core.domain.entities.articles.ArticleResponse

class ArticlesRepositoryImpl(private val dataSource: ArticlesApiDataSource) : ArticlesRepository {
    private val TAG = ArticlesRepositoryImpl::class.simpleName
    override suspend fun getArticles(
            page: Int,
            successCallback: SuccessCallback,
            failureCallback: FailureCallback
    ) {
        dataSource.getArticles(page, object : SuccessCallback {
            override fun onComplete(data: Any?) {
                if (data is ArticleResponse) {
                    //  Every time create new ArticlesMapper instance - not good...
                    val articles = ArticlesMapper().responseToArticles(data)
                    successCallback.onComplete(articles)
                }

            }
        }, object : FailureCallback {
            override fun onFailure(tag: String, error: Any?) {
                failureCallback.onFailure(tag, error)
            }
        }
        )
    }
}