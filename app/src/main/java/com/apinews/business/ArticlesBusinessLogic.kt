package com.apinews.business

import android.content.Intent
import com.core.domain.entities.articles.Article
import com.core.domain.presenter.BasePresenter
import com.core.domain.view.BaseView

interface ArticlesPresenter : BasePresenter {
    fun initView(v: ArticlesView)
}

interface ArticlesView : BaseView{
    fun initRV(articles: ArrayList<Article>)
    fun updateRV(nextPage: ArrayList<Article>)
    fun checkConnection() : Boolean
    fun getStringFromID(id: Int) : String
    fun openLinkInBrowser(intent: Intent)
}

