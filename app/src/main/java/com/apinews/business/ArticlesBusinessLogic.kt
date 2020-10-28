package com.apinews.business

import android.graphics.Bitmap
import com.core.domain.entities.articles.Article
import com.core.domain.presenter.BasePresenter
import com.core.domain.view.BaseView

interface ArticlesPresenter : BasePresenter {
    fun initView(v: ArticlesView)
}

interface ArticlesView : BaseView{
    fun initRV(articles: ArrayList<Article>)
}

