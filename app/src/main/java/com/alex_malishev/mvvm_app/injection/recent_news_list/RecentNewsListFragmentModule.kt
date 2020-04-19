package com.alex_malishev.mvvm_app.injection.recent_news_list

import com.alex_malishev.mvvm_app.injection.qualifiers.PerFragment
import com.alex_malishev.presentation_layer.ui.recent_newslist.RecentNewsListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class RecentNewsListFragmentModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [NewsListModule::class])
    abstract fun newsListFragment(): RecentNewsListFragment
}