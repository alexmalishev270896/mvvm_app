package com.alex_malishev.data_layer.repository

import com.alex_malishev.data_layer.local.INewsLocalSource
import com.alex_malishev.data_layer.mapper.toDomainList
import com.alex_malishev.data_layer.model.NewsAPI
import com.alex_malishev.data_layer.remote.INewsRemoteSource
import com.alex_malishev.data_layer.utils.NoConnectivityException
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(value = MockitoJUnitRunner::class)
class NewsRepositoryTest {

    private val newsRemoteSource: INewsRemoteSource = mock(INewsRemoteSource::class.java)
    private val newsLocalSource: INewsLocalSource = mock(INewsLocalSource::class.java)

    private lateinit var newsRepository: NewsRepository

    @Before
    fun setUp() {
        newsRepository = NewsRepository(newsRemoteSource, newsLocalSource)
    }

    @Test
    fun test_getRecentNewsList_Success() {
        val source = NewsAPI.NewsSourceResponse()
        val articles = listOf(NewsAPI.NewsResponse(source, "title", "12.12.2020"))
        val response = NewsAPI.NewsListResponse("ok", articles)

        val news = response.toDomainList()

        `when`(newsRemoteSource.topHeadlines()).thenReturn(Single.just(response))

        val result = newsRepository.getRecentNewsList().test()

        result.assertNoErrors()
        result.assertValue { it.size == news.size }
        result.assertValue { it[0] == news[0] }
    }

    @Test
    fun test_getRecentNewsList_NoConnectionError() {
        `when`(newsRemoteSource.topHeadlines()).thenReturn(Single.error(NoConnectivityException()))

        val result = newsRepository.getRecentNewsList().test()

        verify(newsRemoteSource, times(1)).topHeadlines()
        result.assertError(NoConnectivityException::class.java)
        result.assertNotComplete()
    }

    @Test
    fun test_search_Success() {
        val source = NewsAPI.NewsSourceResponse()
        val articles = listOf(NewsAPI.NewsResponse(source, "title", "12.12.2020"))
        val response = NewsAPI.NewsListResponse("ok", articles)

        val news = response.toDomainList()
        val query = "title"

        `when`(newsRemoteSource.everything(query)).thenReturn(Single.just(response))

        val result = newsRepository.getNewsBy(query).test()

        verify(newsRemoteSource, times(1)).everything(query)
        result.assertNoErrors()
        result.assertValue { it.size == news.size }
        result.assertValue { it[0] == news[0] }
    }

    @Test
    fun test_search_NoConnectionError() {
        val query = "title"
        `when`(newsRemoteSource.everything(query)).thenReturn(Single.error(NoConnectivityException()))

        val result = newsRepository.getNewsBy(query).test()

        verify(newsRemoteSource, times(1)).everything(query)
        result.assertError(NoConnectivityException::class.java)
        result.assertNotComplete()
    }
}