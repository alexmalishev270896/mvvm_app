package com.alex_malishev.presentation_layer.ui.recent_newslist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.alex_malishev.presentation_layer.R
import com.alex_malishev.presentation_layer.common.ViewState
import com.alex_malishev.presentation_layer.common.observe
import com.alex_malishev.presentation_layer.common.setInteractionEnabled
import com.alex_malishev.presentation_layer.model.NewsParcelable
import com.alex_malishev.presentation_layer.ui.base.BaseFragment
import com.alex_malishev.presentation_layer.ui.base.ControllableAppbarBehaviour
import com.alex_malishev.presentation_layer.ui.base.RecyclerClickListener
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_news_list.*
import javax.inject.Inject

class RecentNewsListFragment : BaseFragment(),
    RecyclerClickListener.OnClickListener<NewsParcelable> {

    @Inject
    lateinit var recentNewsListViewModel: IRecentNewsListViewModel

    @Inject
    lateinit var newsRecyclerAdapter: NewsRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsRecyclerView.layoutManager = LinearLayoutManager(context)
        newsRecyclerView.adapter = newsRecyclerAdapter
        newsRecyclerView.setHasFixedSize(true)
        newsRecyclerAdapter.onItemClickListener = this

        observe(recentNewsListViewModel.newsList, this::handleNewsListState)
        errorPlaceholder.setOnTryAgainClickListener(View.OnClickListener{
            recentNewsListViewModel.getNewsList()
        })
        recentNewsListViewModel.getNewsList()
    }

    override fun onClick(position: Int, item: NewsParcelable) {

    }

    private fun handleNewsListState(state: ViewState<List<NewsParcelable>>) {
        when (state) {
            is ViewState.Loading -> {
                recentProgressBar.visibility = View.VISIBLE
                newsAppbar.setInteractionEnabled(false)
                showPlaceholder(false)
            }
            is ViewState.Error -> {
                recentProgressBar.visibility = View.GONE
                newsAppbar.setInteractionEnabled(false)
                showPlaceholder(true)
            }
            is ViewState.Success -> {
                recentProgressBar.visibility = View.GONE
                showPlaceholder(state.data.isEmpty())
                newsAppbar.setInteractionEnabled(state.data.isNotEmpty())
                newsRecyclerAdapter.clearWithoutNotify()
                newsRecyclerAdapter.addAll(state.data)
            }
        }
    }



    private fun showPlaceholder(isDataEmpty: Boolean){
        if (isDataEmpty) {
            errorPlaceholder.show()
            errorPlaceholder.setText(R.string.something_went_wrong)
        }else {
            errorPlaceholder.hide()
        }
    }
}