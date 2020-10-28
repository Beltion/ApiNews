package com.core.data.articles

import com.core.domain.callbacks.FailureCallback
import com.core.domain.callbacks.SuccessCallback

interface ArticlesRepository {
    suspend fun getArticles(page: Int,
                            successCallback: SuccessCallback,
                            failureCallback: FailureCallback)
}