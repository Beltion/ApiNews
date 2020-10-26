package com.apinews.presentation.articles

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.apinews.R
import com.apinews.business.ArticlesView
import com.core.domain.entities.articles.Article

class ArticlesActivity :
    AppCompatActivity(),
    ArticlesView{

    private val presenter = ArticlesPresenterImpl()

    lateinit var rv : RecyclerView
    lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.articles_list)
        init()
    }

    override fun initRV(articles: ArrayList<Article>) {
        TODO("Not yet implemented")
    }

    override fun init() {
        presenter.initView(this)
        presenter.onViewCreated()
    }

    override fun showContent() {
        rv.visibility = View.VISIBLE
        progress.visibility = View.INVISIBLE
    }

    override fun hideContent() {
        rv.visibility = View.INVISIBLE
        progress.visibility = View.VISIBLE
    }
}