package com.apinews.presentation.articles

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apinews.R
import com.apinews.business.ArticlesView
import com.core.domain.entities.articles.Article

class ArticlesActivity :
    AppCompatActivity(),
    ArticlesView{

    private val presenter = ArticlesPresenterImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    override fun initRV(articles: ArrayList<Article>) {
        TODO("Not yet implemented")
    }

    override fun init() {
        presenter.initView(this)
    }

    override fun showContent() {
        TODO("Not yet implemented")
    }

    override fun hideContent() {
        TODO("Not yet implemented")
    }
}