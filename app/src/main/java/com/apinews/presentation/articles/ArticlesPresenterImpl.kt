package com.apinews.presentation.articles

import android.util.Log
import com.apinews.business.ArticlesPresenter
import com.apinews.business.ArticlesView
import com.core.domain.callbacks.FailureCallback
import com.core.domain.callbacks.SuccessCallback
import com.core.domain.entities.articles.Article
import java.lang.ref.WeakReference

class ArticlesPresenterImpl : ArticlesPresenter {

    private var view: WeakReference<ArticlesView>? = null
    private val model = ArticlesModel()
    private val TAG = ArticlesPresenterImpl::class.simpleName


    override fun onViewCreated() {
        Log.d(TAG, "Before load Articles")
        model.getArticles(object : SuccessCallback{
            override fun onComplete(data: Any?) {
                (data as ArrayList<Article>).let{
                    Log.d(TAG, it.size.toString())
                }

            }

        }, object : FailureCallback{
            override fun onFailure(tag: String, error: Any?) {
                Log.e(TAG, tag + error.toString())
            }

        })
    }

    override fun initView(v: ArticlesView) {
        view = WeakReference(v)
    }
}