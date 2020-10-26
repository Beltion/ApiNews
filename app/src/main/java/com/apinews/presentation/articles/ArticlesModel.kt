package com.apinews.presentation.articles

import com.apinews.data.ArticlesRepositoryImpl
import com.apinews.framework.ArticlesApiDataSource
import com.core.domain.callbacks.FailureCallback
import com.core.domain.callbacks.SuccessCallback
import com.core.usecase.GetArticles
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ArticlesModel {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    private var currentPage = 1

    fun getArticles(successCallback: SuccessCallback,
                    failureCallback: FailureCallback) = scope.launch {
        val getArticlesUseCase = GetArticles(ArticlesRepositoryImpl(ArticlesApiDataSource(currentPage)))

        getArticlesUseCase.getArticles(object : SuccessCallback{
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