package com.apinews.business.adaprets

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apinews.R
import com.core.domain.entities.articles.Article
import com.squareup.picasso.Picasso

class ArticlesAdapter(
        private val articles: ArrayList<Article>,
        private val onBottomReachedListener: OnBottomReachedListener
): RecyclerView.Adapter<ArticlesAdapter.ArticlesVH>() {


    class ArticlesVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.img_article_card)
        val title = itemView.findViewById<TextView>(R.id.title_article_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesVH {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val articleCard = inflater.inflate(R.layout.article_card, parent, false)
        return ArticlesVH(articleCard)
    }

    override fun onBindViewHolder(holder: ArticlesVH, position: Int) {
        Log.d("RVVV", "position: $position")
        holder.title.text = articles[position].title

        Picasso.get()
            .load(articles[position].urlToImage)
            .fit()
            .into(holder.imageView)

        if(position == articles.size - 1){
            onBottomReachedListener.onBottomReached()
        }

    }

    override fun getItemCount() = articles.size

    interface OnBottomReachedListener{
        fun onBottomReached()
    }

    fun addData(nextPage: ArrayList<Article>){
        Log.d("RVVV", "size: $itemCount")
        val oldSize = articles.size
        articles.addAll(nextPage)
        notifyItemRangeInserted(oldSize, nextPage.size)
    }
}