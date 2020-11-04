package com.apinews.business

import android.content.Intent
import com.core.domain.entities.articles.Article
import com.core.domain.presenter.BasePresenter
import com.core.domain.view.BaseView

interface ArticlesPresenter : BasePresenter {
    fun initView(v: ArticlesView)
    fun onBottomReached() //    Adding news when the end of the list is reached
    fun onFailureAction(error: Any?)
    fun omItemClick(link: String)
}

interface ArticlesView : BaseView{
//    Init/Update RecyclerView
    fun initRV(articles: ArrayList<Article>)
    fun updateRV(nextPage: ArrayList<Article>)

//  Check Internet
    fun checkConnection() : Boolean

    fun getStringFromID(id: Int) : String
    fun openLinkInBrowser(intent: Intent)
}

