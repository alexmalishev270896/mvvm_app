package com.alex_malishev.presentation_layer.ui.recent_newslist

import androidx.lifecycle.LiveData
import com.alex_malishev.presentation_layer.common.ViewState
import com.alex_malishev.presentation_layer.model.NewsParcelable

interface IRecentNewsListViewModel {
    val newsList: LiveData<ViewState<List<NewsParcelable>>>
    fun getNewsList()
}