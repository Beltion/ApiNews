package com.apinews.presentation.articles

import android.content.Intent
import android.net.Uri
import android.util.Log
import com.apinews.R
import com.apinews.business.ArticlesPresenter
import com.apinews.business.ArticlesView
import com.core.domain.callbacks.FailureCallback
import com.core.domain.callbacks.SuccessCallback
import com.core.domain.entities.articles.Article
import com.core.domain.entities.articles.ArticleErrorResponse
import java.io.IOException
import java.lang.NullPointerException
import java.lang.ref.WeakReference

class ArticlesPresenterImpl : ArticlesPresenter {

    private var view: WeakReference<ArticlesView>? = null
    private val model = ArticlesModel()
    private val TAG = ArticlesPresenterImpl::class.simpleName


    override fun onViewCreated() {
        Log.d(TAG, "network -> ${view?.get()?.checkConnection()}")
        view?.get()?.let { articlesView ->
            if(articlesView.checkConnection()){
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
            } else {
                articlesView.showText(
                        articlesView.getStringFromID(R.string.no_internet)
                )
            }
        }

    }

    override fun initView(v: ArticlesView) {
        view = WeakReference(v)
    }

    fun onBottomReached(){
        view?.get()?.let {articlesView ->
            if(articlesView.checkConnection()){
                Log.d(TAG, "onBottomReached currentPage -> ${model.currentPage}")
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
                        onFailureAction(error)
                    }

                })
            } else {
                articlesView.showText(
                        articlesView.getStringFromID(R.string.no_internet)
                )
            }
        }

    }

    fun onFailureAction(error: Any?){
        view?.get()?.let {articlesView ->
            val errorString = when(error){
                is ArticleErrorResponse -> {
                    if(error.code == "maximumResultsReached"){
                        articlesView.getStringFromID(R.string.too_many_results)
                    } else {
                        articlesView.getStringFromID(R.string.another_article_response_error)
                    }
                }
                is Exception -> articlesView.getStringFromID(
                        onFailureException(error)
                )
                else -> articlesView.getStringFromID(R.string.some_error)
            }
            articlesView.showText(errorString)
        }
    }

    private fun onFailureException(e: Exception) = when(e){
        is IOException -> R.string.load_failed
        else -> R.string.some_error
    }

    fun omItemClick(link: String) {
        if(!link.isBlank()){
            try {
                val url = Uri.parse(link)
                val intent = Intent(Intent.ACTION_VIEW, url)
                view?.get()?.openLinkInBrowser(intent)
            }catch (e: NullPointerException){
                e.printStackTrace()
            }
        }

    }
}