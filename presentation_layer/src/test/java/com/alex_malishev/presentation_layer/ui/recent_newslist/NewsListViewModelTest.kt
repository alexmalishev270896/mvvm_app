package com.alex_malishev.presentation_layer.ui.recent_newslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.alex_malishev.domain_layer.model.News
import com.alex_malishev.domain_layer.use_case.news.IGetRecentNewsUseCase
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
import org.mockito.ArgumentMatchers.*
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(value = MockitoJUnitRunner::class)
class NewsListViewModelTest {

    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()
    private val getRecentNewsListUseCase: IGetRecentNewsUseCase = mock(IGetRecentNewsUseCase::class.java)
    private val observer: Observer<ViewState<List<NewsParcelable>>> = mock(Observer::class.java) as Observer<ViewState<List<NewsParcelable>>>
    private val schedulerProvider: BaseSchedulerProvider = TestSchedulerProvider()
    private lateinit var newsListViewModel: NewsListViewModel

    @Before
    fun setUp() {
        newsListViewModel = NewsListViewModel(getRecentNewsListUseCase, schedulerProvider)
    }

    @Test
    fun test_getNewsList_Success() {
        // Assemble
        val news = listOf(News("title1", "date1"))
        val newsParcelableList = listOf(NewsParcelable("title1", "date1"))
        `when`(getRecentNewsListUseCase.invoke()).thenReturn(Single.just(news))

        // Act
        newsListViewModel.newsList.observeForever(observer)
        newsListViewModel.getNewsList()

        //Assert
        verify(getRecentNewsListUseCase, times(1)).invoke()
        val liveData = newsListViewModel.newsList
        verify(observer, times(1)).onChanged(ViewState.Loading(emptyList()))
        assert(liveData.value is ViewState.Success)
        assert((liveData.value as ViewState.Success).data.size == newsParcelableList.size)
        assert((liveData.value as ViewState.Success).data[0] == newsParcelableList[0])
    }

    @Test
    fun test_getNewsList_Error() {
        // Assemble
        `when`(getRecentNewsListUseCase.invoke()).thenReturn(Single.error(RuntimeException()))

        // Act
        newsListViewModel.newsList.observeForever(observer)
        newsListViewModel.getNewsList()

        //Assert
        verify(getRecentNewsListUseCase, times(1)).invoke()
        val liveData = newsListViewModel.newsList
        verify(observer, times(1)).onChanged(ViewState.Loading(emptyList()))
        assert(liveData.value is ViewState.Error)
        assert((liveData.value as ViewState.Error).data == null)
    }
}