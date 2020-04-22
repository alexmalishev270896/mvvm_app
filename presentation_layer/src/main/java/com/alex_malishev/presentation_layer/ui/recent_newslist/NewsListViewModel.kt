package com.alex_malishev.presentation_layer.ui.recent_newslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alex_malishev.domain_layer.use_case.news.IGetRecentNewsUseCase
import com.alex_malishev.presentation_layer.common.BaseSchedulerProvider
import com.alex_malishev.presentation_layer.common.ViewState
import com.alex_malishev.presentation_layer.common.toParcelable
import com.alex_malishev.presentation_layer.model.NewsParcelable
import com.alex_malishev.presentation_layer.ui.base.BaseViewModel
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class NewsListViewModel @Inject constructor(
    private val getRecentNewsUseCase: IGetRecentNewsUseCase,
    private val schedulerProvider: BaseSchedulerProvider
) : BaseViewModel(), IRecentNewsListViewModel {

    private val mNewsListData = MutableLiveData<ViewState<List<NewsParcelable>>>()
    override val newsList: LiveData<ViewState<List<NewsParcelable>>> = mNewsListData

    override fun getNewsList() {
        getRecentNewsUseCase()
            .map { list -> list.map { it.toParcelable() } }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .doOnSubscribe { mNewsListData.value = ViewState.Loading(emptyList()) }
            .subscribeBy(
                onSuccess = { mNewsListData.value = ViewState.Success(it) },
                onError = { throwable -> mNewsListData.value = ViewState.Error(throwable) }
            ).track()
    }


}