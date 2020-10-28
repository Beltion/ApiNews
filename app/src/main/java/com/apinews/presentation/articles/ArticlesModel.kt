package com.apinews.presentation.articles

import com.apinews.data.repositories.ArticlesRepositoryImpl
import com.apinews.framework.articles.ArticlesApiDataSource
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
        val getArticlesUseCase = GetArticles(ArticlesRepositoryImpl(ArticlesApiDataSource()))

        getArticlesUseCase.getArticles(currentPage, object : SuccessCallback{
            override fun onComplete(data: Any?) {
                if (data is ArrayList<*>){
                    successCallback.onComplete(data)
                }

            }

        },  object : FailureCallback{
            override fun onFailure(tag: String, error: Any?) {
                failureCallback.onFailure(tag, error)
            }

        })
    }

}