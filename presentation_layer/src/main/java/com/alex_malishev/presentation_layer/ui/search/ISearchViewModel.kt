package com.alex_malishev.presentation_layer.ui.search

import androidx.lifecycle.LiveData
import com.alex_malishev.presentation_layer.common.ViewState
import com.alex_malishev.presentation_layer.model.NewsParcelable

interface ISearchViewModel {

    val searchResult: LiveData<ViewState<List<NewsParcelable>>>
    fun search(query: String)
}