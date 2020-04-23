package com.alex_malishev.mvvm_app.injection

import com.alex_malishev.mvvm_app.injection.qualifiers.PerActivity
import com.alex_malishev.mvvm_app.injection.recent_news_list.RecentNewsListFragmentModule
import com.alex_malishev.mvvm_app.injection.saved_news.SavedNewsFragmentModule
import com.alex_malishev.mvvm_app.injection.search.SearchFragmentModule
import com.alex_malishev.presentation_layer.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [AndroidSupportInjectionModule::class])
abstract class ActivityModule {

    @PerActivity
    @ContributesAndroidInjector(
        modules = [
            RecentNewsListFragmentModule::class,
            SearchFragmentModule::class,
            SavedNewsFragmentModule::class
        ]
    )
    abstract fun mainActivity(): MainActivity
}