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
        tryAgainButton.setOnClickListener {
            recentNewsListViewModel.getNewsList()
        }
        recentNewsListViewModel.getNewsList()
    }

    override fun onClick(position: Int, item: NewsParcelable) {

    }

    private fun handleNewsListState(state: ViewState<List<NewsParcelable>>) {
        when (state) {
            is ViewState.Loading -> {
                recentProgressBar.show()
                newsAppbar.setInteractionEnabled(false)
                errorPlaceholder.visibility = View.GONE
            }
            is ViewState.Error -> {
                recentProgressBar.hide()
                newsAppbar.setInteractionEnabled(false)
                errorPlaceholder.visibility = View.VISIBLE
            }
            is ViewState.Success -> {
                recentProgressBar.hide()
                newsAppbar.setInteractionEnabled(state.data.isNotEmpty())
                newsRecyclerAdapter.clearWithoutNotify()
                newsRecyclerAdapter.addAll(state.data)
            }
        }
    }

    private fun AppBarLayout.setInteractionEnabled(isEnabled: Boolean) {
        val params = layoutParams as CoordinatorLayout.LayoutParams
        (params.behavior as ControllableAppbarBehaviour).isEnabled = isEnabled
        layoutParams = params
    }
}