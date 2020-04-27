package com.alex_malishev.presentation_layer.ui.recent_newslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alex_malishev.presentation_layer.R
import com.alex_malishev.presentation_layer.model.NewsParcelable
import com.alex_malishev.presentation_layer.ui.base.BaseRecyclerAdapter
import com.alex_malishev.presentation_layer.utils.DateUtils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_news.view.*
import javax.inject.Inject

class NewsRecyclerAdapter @Inject constructor() : BaseRecyclerAdapter<NewsRecyclerAdapter.NewsHolder, NewsParcelable>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        return NewsHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_news,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        get(position)?.let { holder.bind(it) }
    }

    class NewsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(news: NewsParcelable) {
            itemView.newsTitle.text = news.title
            itemView.newsSource.text = news.source?.name
            itemView.newsDate.text = DateUtils.toShortDate(news.date)
            if (!news.imageUrl.isNullOrEmpty()){
                itemView.newsImage.visibility = View.VISIBLE
                Glide.with(itemView)
                    .load(news.imageUrl)
                    .placeholder(R.drawable.placeholder_news)
                    .into(itemView.newsImage)
            }else {
                itemView.newsImage.visibility = View.GONE
            }

        }
    }
}