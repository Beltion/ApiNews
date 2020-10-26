package com.apinews.data.repositories

import com.apinews.data.mappers.ArticlesMapper
import com.apinews.framework.ArticlesApiDataSource
import com.core.data.articles.ArticlesRepository
import com.core.domain.callbacks.FailureCallback
import com.core.domain.callbacks.SuccessCallback
import com.core.domain.entities.articles.ArticleResponse

class ArticlesRepositoryImpl(private val dataSource: ArticlesApiDataSource) : ArticlesRepository {
    override suspend fun getArticles(
            successCallback: SuccessCallback,
            failureCallback: FailureCallback
    ) {
        dataSource.getArticles(object : SuccessCallback {
            override fun onComplete(data: Any?) {
                if(data is ArticleResponse){
                    successCallback.onComplete(
                            //  Every time create new ArticlesMapper instance - not good...
                            ArticlesMapper().responseToArticles(data)
                    )
                }

            }

        }, object : FailureCallback {
            override fun onFailure(tag: String, error: Any?) {
                failureCallback.onFailure(tag, error)
            }

        })
    }
}