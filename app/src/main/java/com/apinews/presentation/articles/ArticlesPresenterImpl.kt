package com.apinews.presentation.articles

import android.graphics.Bitmap
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
                Log.d(TAG, "Before load Articles Images")
                (data as ArrayList<Article>).let{
                    view?.get()?.initRV(data)
                    view?.get()?.showContent()
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

    fun onBottomReached(){

        Log.d("$TAG", "onBottomReached currentPage -> ${model.currentPage}")
        model.getArticles(object : SuccessCallback{
            override fun onComplete(data: Any?) {
                Log.d(TAG, "Before load Articles Images")
                (data as ArrayList<Article>).let{
                    Log.d("$TAG data size", data.size.toString())
                    view?.get()?.updateRV(data)
                }

            }

        }, object : FailureCallback{
            override fun onFailure(tag: String, error: Any?) {
                Log.e(TAG, tag + error.toString())
            }

        })
    }
}