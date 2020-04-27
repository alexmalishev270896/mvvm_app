package com.alex_malishev.presentation_layer.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.alex_malishev.domain_layer.model.News
import com.alex_malishev.domain_layer.use_case.search.ISearchUseCase
import com.alex_malishev.presentation_layer.common.BaseSchedulerProvider
import com.alex_malishev.presentation_layer.common.TestSchedulerProvider
import com.alex_malishev.presentation_layer.common.ViewState
import com.alex_malishev.presentation_layer.model.NewsParcelable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(value = MockitoJUnitRunner::class)
class SearchViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val searchUseCase: ISearchUseCase = mock(ISearchUseCase::class.java)
    private val observer: Observer<ViewState<List<NewsParcelable>>> = mock(Observer::class.java) as Observer<ViewState<List<NewsParcelable>>>
    private val schedulerProvider: BaseSchedulerProvider = TestSchedulerProvider()
    private lateinit var searchViewModel: SearchViewModel


    @Before
    fun setUp() {
        searchViewModel = SearchViewModel(searchUseCase, schedulerProvider)
    }

    @Test
    fun test_search_Success_HasData() {
        //Assemble
        val testQuery = "apple"
        val news = listOf(News("apple", "date1"))
        val newsParcelableList = listOf(NewsParcelable("apple", "date1"))
        `when`(searchUseCase.invoke(testQuery)).thenReturn(Single.just(news))

        //Act
        searchViewModel.searchResult.observeForever(observer)
        searchViewModel.search(testQuery)

        //Assert
        verify(searchUseCase, times(1)).invoke(testQuery)
        val data = searchViewModel.searchResult
        verify(observer, times(1)).onChanged(ViewState.Loading(emptyList()))
        assert(data.value is ViewState.Success)
        assert((data.value as ViewState.Success).data.size == newsParcelableList.size)
        assert((data.value as ViewState.Success).data[0] == newsParcelableList[0])
    }

    @Test
    fun test_search_Success_Empty() {
        //Assemble
        val testQuery = "afdfsd"
        `when`(searchUseCase.invoke(testQuery)).thenReturn(Single.just(emptyList()))

        //Act
        searchViewModel.searchResult.observeForever(observer)
        searchViewModel.search(testQuery)

        //Assert
        verify(searchUseCase, times(1)).invoke(testQuery)
        val data = searchViewModel.searchResult
        verify(observer, times(1)).onChanged(ViewState.Loading(emptyList()))
        assert(data.value is ViewState.Success)
        assert((data.value as ViewState.Success).data.isEmpty())
    }

    @Test
    fun test_search_Error() {
        //Assemble
        `when`(searchUseCase.invoke(anyString())).thenReturn(Single.error(Exception()))

        //Act
        searchViewModel.searchResult.observeForever(observer)
        searchViewModel.search(anyString())

        //Assert
        verify(searchUseCase, times(1)).invoke(anyString())
        val data = searchViewModel.searchResult
        verify(observer, times(1)).onChanged(ViewState.Loading(emptyList()))
        assert(data.value is ViewState.Error)
        assert((data.value as ViewState.Error).data.isNullOrEmpty())
    }
}