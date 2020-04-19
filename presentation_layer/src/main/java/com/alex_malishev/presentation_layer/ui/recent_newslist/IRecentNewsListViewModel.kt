package com.alex_malishev.presentation_layer.ui.recent_newslist

import androidx.lifecycle.LiveData
import com.alex_malishev.presentation_layer.model.NewsParcelable

interface IRecentNewsListViewModel {

    val newsList: LiveData<List<NewsParcelable>>
    val error: LiveData<String>
    fun getNewsList()
}