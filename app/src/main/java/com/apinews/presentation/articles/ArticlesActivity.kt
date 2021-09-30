package com.apinews.presentation.articles

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apinews.R
import com.apinews.business.ArticlesView
import com.apinews.business.adaprets.ArticlesAdapter
import com.core.domain.entities.articles.Article


class ArticlesActivity :
    AppCompatActivity(),
    ArticlesView, ArticlesAdapter.OnArticleAdapterListener {

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

    override fun showText(string: String) {
        Toast.makeText(applicationContext, string, Toast.LENGTH_LONG).show()
    }

    override fun onBottomReached() {
        presenter.onBottomReached()
    }

    override fun onItemClick(link: String) {
        presenter.omItemClick(link)
    }

    override fun updateRV(nextPage: ArrayList<Article>){
        (rv.adapter as ArticlesAdapter).addData(nextPage)
    }

    override fun checkConnection() : Boolean{
        val baseContext = this.baseContext ?: return false
        val connectivityManager = baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    override fun getStringFromID(id: Int) = applicationContext.getString(id)

    override fun openLinkInBrowser(intent: Intent) {
        startActivity(intent)
    }
}