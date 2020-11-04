package com.apinews.business.adaprets

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
        private val onArticleAdapterListener: OnArticleAdapterListener
): RecyclerView.Adapter<ArticlesAdapter.ArticlesVH>() {


    class ArticlesVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.img_article_card)
        val title = itemView.findViewById<TextView>(R.id.title_article_card)
        val description = itemView.findViewById<TextView>(R.id.description_article_card)

        fun itemClick(link: String, cl: OnArticleAdapterListener){
            itemView.setOnClickListener {
                cl.onItemClick(link)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesVH {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val articleCard = inflater.inflate(R.layout.article_card, parent, false)
        return ArticlesVH(articleCard)
    }

    override fun onBindViewHolder(holder: ArticlesVH, position: Int) {
        holder.title.text = articles[position].title
        holder.description.text = articles[position].description
        holder.itemClick(
                articles[position].url,
                onArticleAdapterListener
        )

//        It was possible to make the upload in the main thread,
//        but it was not possible to transfer it to another thread.
//        So it was decided to use Picasso
        Picasso.get()
            .load(articles[position].urlToImage)
            .fit()
            .into(holder.imageView)

        if(position == articles.size - 1){
            onArticleAdapterListener.onBottomReached()
        }

    }

    override fun getItemCount() = articles.size

    interface OnArticleAdapterListener{
        fun onBottomReached()
        fun onItemClick(link: String)
    }

//  Update articles
    fun addData(nextPage: ArrayList<Article>){
        val oldSize = articles.size
        articles.addAll(nextPage)
        notifyItemRangeInserted(oldSize, nextPage.size)
    }
}