package com.alex_malishev.mvvm_app.injection.saved_news

import com.alex_malishev.mvvm_app.injection.qualifiers.PerFragment
import com.alex_malishev.presentation_layer.ui.saved_news.SavedNewsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SavedNewsFragmentModule {

    @PerFragment
    @ContributesAndroidInjector
    abstract fun savedNewsFragment() : SavedNewsFragment
}