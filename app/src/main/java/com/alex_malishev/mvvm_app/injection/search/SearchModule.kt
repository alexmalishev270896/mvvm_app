package com.alex_malishev.mvvm_app.injection.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alex_malishev.domain_layer.repository.news.INewsRepository
import com.alex_malishev.domain_layer.use_case.search.ISearchUseCase
import com.alex_malishev.domain_layer.use_case.search.SearchUseCase
import com.alex_malishev.mvvm_app.injection.NewsModule
import com.alex_malishev.mvvm_app.injection.ViewModelFactory
import com.alex_malishev.mvvm_app.injection.qualifiers.ViewModelKey
import com.alex_malishev.presentation_layer.ui.search.ISearchViewModel
import com.alex_malishev.presentation_layer.ui.search.SearchFragment
import com.alex_malishev.presentation_layer.ui.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [NewsModule::class])
abstract class SearchModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideUseCase(newsRepository: INewsRepository): ISearchUseCase{
            return SearchUseCase(newsRepository)
        }

        @Provides
        @JvmStatic
        fun provideViewModel(viewModelFactory: ViewModelFactory, fragment: SearchFragment): ISearchViewModel{
            return ViewModelProvider(fragment, viewModelFactory).get(SearchViewModel::class.java)
        }
    }
}