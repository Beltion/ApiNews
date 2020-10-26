package com.apinews.presentation.articles

import android.util.Log
import com.apinews.business.ArticlesPresenter
import com.apinews.business.ArticlesView
import com.core.domain.callbacks.FailureCallback
import com.core.domain.callbacks.SuccessCallback
import java.lang.ref.WeakReference

class ArticlesPresenterImpl : ArticlesPresenter {

    private var view: WeakReference<ArticlesView>? = null
    private val model = ArticlesModel()
    private val tag = ArticlesPresenterImpl::class.simpleName


    override fun onViewCreated() {
        Log.d(tag, "Before load Articles")
        model.getArticles(object : SuccessCallback{
            override fun onComplete(data: Any?) {
                Log.d(tag, data.toString())
            }

        }, object : FailureCallback{
            override fun onFailure(error: String) {
                Log.e(tag, error)
            }

        })
    }

    override fun initView(v: ArticlesView) {
        view = WeakReference(v)
    }
}