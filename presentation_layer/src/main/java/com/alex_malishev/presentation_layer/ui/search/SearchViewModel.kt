package com.alex_malishev.presentation_layer.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alex_malishev.domain_layer.use_case.search.ISearchUseCase
import com.alex_malishev.presentation_layer.common.BaseSchedulerProvider
import com.alex_malishev.presentation_layer.common.ViewState
import com.alex_malishev.presentation_layer.common.toParcelable
import com.alex_malishev.presentation_layer.model.NewsParcelable
import com.alex_malishev.presentation_layer.ui.base.BaseViewModel
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchUseCase: ISearchUseCase,
    private val schedulerProvider: BaseSchedulerProvider
) : BaseViewModel(), ISearchViewModel {

    private val mSearchResult = MutableLiveData<ViewState<List<NewsParcelable>>>()
    override val searchResult: LiveData<ViewState<List<NewsParcelable>>> = mSearchResult

    override fun search(query: String) {
        searchUseCase(query)
            .map { list-> list.map { it.toParcelable() } }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .doOnSubscribe { mSearchResult.value = ViewState.Loading(emptyList()) }
            .subscribeBy(
                onSuccess = { mSearchResult.value = ViewState.Success(it) },
                onError = {
                    mSearchResult.value = ViewState.Error(it)
                    it.printStackTrace()
                }
            ).track()
    }
}