package com.alex_malishev.presentation_layer.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.alex_malishev.presentation_layer.R
import com.alex_malishev.presentation_layer.common.ViewState
import com.alex_malishev.presentation_layer.common.observe
import com.alex_malishev.presentation_layer.model.NewsParcelable
import com.alex_malishev.presentation_layer.ui.base.BaseFragment
import com.alex_malishev.presentation_layer.ui.recent_newslist.NewsRecyclerAdapter
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.sellmair.disposer.disposeBy
import io.sellmair.disposer.onDestroy
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchFragment : BaseFragment() {

    @Inject
    lateinit var searchViewModel: ISearchViewModel

    @Inject
    lateinit var newsRecyclerAdapter: NewsRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchResultRecyclerView.adapter = newsRecyclerAdapter
        searchResultRecyclerView.layoutManager = LinearLayoutManager(context)
        observe(searchViewModel.searchResult, this::handleSearchResult)
        searchView.textChangedEvent
            .debounce(300, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
            .filter { it.length > 2 }
            .distinctUntilChanged()
            .subscribe {
                searchViewModel.search(it)
            }.disposeBy(onDestroy)

        searchResultRecyclerView.setOnTouchListener { v, _ -> v.requestFocus(); false }
    }



    private fun handleSearchResult(state: ViewState<List<NewsParcelable>>) {
        when (state) {
            is ViewState.Success -> {
                searchView.hideProgress()
                showPlaceholder(state.data.isEmpty())
                newsRecyclerAdapter.clear()
                newsRecyclerAdapter.addAll(state.data)
            }
            is ViewState.Error -> {
                showPlaceholder(true)
                searchView.hideProgress()

            }
            is ViewState.Loading -> {
                searchView.showProgress()
            }
        }
    }

    private fun showPlaceholder(isDataEmpty: Boolean){
        if (isDataEmpty) {
            emptyPlaceholder.show()
        }else {
            emptyPlaceholder.hide()
        }
    }
}