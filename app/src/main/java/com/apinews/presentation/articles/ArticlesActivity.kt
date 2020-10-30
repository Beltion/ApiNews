package com.apinews.presentation.articles

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apinews.R
import com.apinews.business.ArticlesView
import com.apinews.business.adaprets.ArticlesAdapter
import com.core.domain.entities.articles.Article

class ArticlesActivity :
    AppCompatActivity(),
    ArticlesView, ArticlesAdapter.OnBottomReachedListener {

    private val presenter = ArticlesPresenterImpl()

    lateinit var rv : RecyclerView
    lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.articles_list)
        init()
    }

    override fun initRV(articles: ArrayList<Article>) {
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val adapter = ArticlesAdapter(articles, this)
        rv.adapter = adapter

    }


    override fun init() {
        rv = findViewById(R.id.rv_articles)
        progressBar = findViewById(R.id.progress_bar)

        presenter.initView(this)
        presenter.onViewCreated()
    }

    override fun showContent() {
        rv.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE
    }

    override fun hideContent() {
        rv.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }

    override fun onBottomReached() {
        presenter.onBottomReached()
    }

    override fun updateRV(nextPage: ArrayList<Article>){
        (rv.adapter as ArticlesAdapter).addData(nextPage)
    }

    interface UpdateRVData{
        fun update(arrayList: ArrayList<Article>)
    }
}