package com.core.data.articles

import com.core.domain.callbacks.FailureCallback
import com.core.domain.callbacks.SuccessCallback

interface ArticlesRepository {
    suspend fun getArticles(successCallback: SuccessCallback, failureCallback: FailureCallback)
}