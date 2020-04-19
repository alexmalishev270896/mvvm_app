package com.alex_malishev.presentation_layer.ui.recent_newslist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alex_malishev.domain_layer.use_case.news.IGetRecentNewsUseCase
import com.alex_malishev.presentation_layer.common.toParcelable
import com.alex_malishev.presentation_layer.model.NewsParcelable
import com.alex_malishev.presentation_layer.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsListViewModel @Inject constructor(
    private val getRecentNewsUseCase: IGetRecentNewsUseCase
) : BaseViewModel(), IRecentNewsListViewModel {

    init {
        Log.e("NewsListViewModel", "create view model")
    }

    private val mNewsListData = MutableLiveData<List<NewsParcelable>>()
    private val mErrorData = MutableLiveData<String>()

    override val newsList: LiveData<List<NewsParcelable>> = mNewsListData
    override val error: LiveData<String> = mErrorData

    override fun getNewsList() {
        getRecentNewsUseCase()
            .map { list -> list.map { it.toParcelable() } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { mNewsListData.value = it },
                onError = { throwable -> throwable.message?.let { mErrorData.value = it} }
            ).track()
    }


}