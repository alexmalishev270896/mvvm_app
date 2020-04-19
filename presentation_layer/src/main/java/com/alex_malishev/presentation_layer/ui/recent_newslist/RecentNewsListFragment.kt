package com.alex_malishev.presentation_layer.ui.recent_newslist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.alex_malishev.presentation_layer.R
import com.alex_malishev.presentation_layer.common.observe
import com.alex_malishev.presentation_layer.model.NewsParcelable
import com.alex_malishev.presentation_layer.ui.base.BaseFragment
import com.alex_malishev.presentation_layer.ui.base.RecyclerClickListener
import kotlinx.android.synthetic.main.fragment_news_list.*
import javax.inject.Inject

class RecentNewsListFragment : BaseFragment(), RecyclerClickListener.OnClickListener<NewsParcelable> {

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
        Log.e("NewsListFragment", "on view created")
        newsRecyclerView.layoutManager = LinearLayoutManager(context)
        newsRecyclerView.adapter = newsRecyclerAdapter
        newsRecyclerView.setHasFixedSize(true)
        newsRecyclerAdapter.onItemClickListener = this

        observe(recentNewsListViewModel.newsList){
            newsRecyclerAdapter.clearWithoutNotify()
            newsRecyclerAdapter.addAll(it)
        }
        recentNewsListViewModel.getNewsList()
    }

    override fun onClick(position: Int, item: NewsParcelable) {

    }
}