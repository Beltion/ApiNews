package com.core.usecase

import com.core.data.articles.ArticlesRepository
import com.core.domain.callbacks.FailureCallback
import com.core.domain.callbacks.SuccessCallback

class GetArticles(private val repository: ArticlesRepository) {
    suspend fun getArticles(successCallback: SuccessCallback, failureCallback: FailureCallback) {
        repository.getArticles(object : SuccessCallback{
            override fun onComplete(data: Any?) {
                successCallback.onComplete(data)
            }

        },  object : FailureCallback{
            override fun onFailure(tag: String, error: Any?) {
                failureCallback.onFailure(tag, error)
            }

        })
    }

}