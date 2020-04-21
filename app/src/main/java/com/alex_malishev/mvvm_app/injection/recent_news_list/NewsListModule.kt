package com.alex_malishev.mvvm_app.injection.recent_news_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alex_malishev.domain_layer.repository.news.INewsRepository
import com.alex_malishev.domain_layer.use_case.news.GetRecentNewsUseCase
import com.alex_malishev.domain_layer.use_case.news.IGetRecentNewsUseCase
import com.alex_malishev.mvvm_app.injection.NewsModule
import com.alex_malishev.mvvm_app.injection.SchedulerModule
import com.alex_malishev.mvvm_app.injection.ViewModelFactory
import com.alex_malishev.mvvm_app.injection.qualifiers.ViewModelKey
import com.alex_malishev.presentation_layer.ui.recent_newslist.IRecentNewsListViewModel
import com.alex_malishev.presentation_layer.ui.recent_newslist.NewsListViewModel
import com.alex_malishev.presentation_layer.ui.recent_newslist.RecentNewsListFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [NewsModule::class])
abstract class NewsListModule {

    @Binds
    @IntoMap
    @ViewModelKey(NewsListViewModel::class)
    abstract fun bindNewsListViewModel(viewModel: NewsListViewModel): ViewModel

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideGetNewsListUseCase(newsRepository: INewsRepository): IGetRecentNewsUseCase {
            return GetRecentNewsUseCase(newsRepository)
        }

        @Provides
        @JvmStatic
        fun provideViewModel(viewModelFactory: ViewModelFactory, fragmentRecent: RecentNewsListFragment): IRecentNewsListViewModel{
            return ViewModelProvider(fragmentRecent, viewModelFactory).get(NewsListViewModel::class.java)
        }
    }



}