package com.apinews.presentation.articles

import com.apinews.business.ArticlesPresenter
import com.apinews.business.ArticlesView
import java.lang.ref.WeakReference

class ArticlesPresenterImpl : ArticlesPresenter {

    private var view: WeakReference<ArticlesView>? = null

    override fun onViewCreated() {
        TODO("Not yet implemented")
    }

    override fun initView(v: ArticlesView) {
        view = WeakReference(v)
    }
}