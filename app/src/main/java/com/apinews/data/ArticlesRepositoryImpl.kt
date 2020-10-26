package com.apinews.data

import com.apinews.framework.ArticlesApiDataSource
import com.core.data.articles.ArticlesRepository
import com.core.domain.callbacks.FailureCallback
import com.core.domain.callbacks.SuccessCallback

class ArticlesRepositoryImpl(private val dataSource: ArticlesApiDataSource) : ArticlesRepository {
    override suspend fun getArticles(
        successCallback: SuccessCallback,
        failureCallback: FailureCallback
    ) {
        dataSource.getArticles(object : SuccessCallback{
            override fun onComplete(data: Any?) {
                successCallback.onComplete(data)
            }

        }, object : FailureCallback{
            override fun onFailure(tag: String, error: Any?) {
                failureCallback.onFailure(tag, error)
            }

        })
    }
}