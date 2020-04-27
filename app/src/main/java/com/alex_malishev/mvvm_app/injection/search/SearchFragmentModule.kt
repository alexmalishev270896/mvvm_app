package com.alex_malishev.mvvm_app.injection.search

import com.alex_malishev.mvvm_app.injection.qualifiers.PerFragment
import com.alex_malishev.presentation_layer.ui.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SearchFragmentModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [SearchModule::class])
    abstract fun searchFragment(): SearchFragment
}