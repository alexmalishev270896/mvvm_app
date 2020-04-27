package com.alex_malishev.domain_layer.use_case.search

import com.alex_malishev.domain_layer.model.News
import com.alex_malishev.domain_layer.repository.news.INewsRepository
import io.reactivex.Single
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(value = MockitoJUnitRunner::class)
class SearchUseCaseTest {

    private val newsRepository: INewsRepository = Mockito.mock(INewsRepository::class.java)

    private lateinit var searchUseCase: SearchUseCase

    @Before
    fun setUp(){
        searchUseCase = SearchUseCase(newsRepository)
    }

    @Test
    fun test_invoke_Success() {
        val news = listOf(News("News", "12.12.2020"))
        val query = "news"
        `when`(newsRepository.getNewsBy(query)).thenReturn(Single.just(news))

        val list = searchUseCase(query).test()

        verify(newsRepository, times(1)).getNewsBy(query)
        list.assertNoErrors()
        list.assertValue { it.isNotEmpty() }
        list.assertValue { it.size == news.size }
    }

    @Test
    fun test_invoke_Error() {
        val query = "news"
        `when`(newsRepository.getNewsBy(query)).thenReturn(Single.error(RuntimeException()))

        searchUseCase(query).test()
            .assertError(RuntimeException::class.java)
        verify(newsRepository, times(1)).getNewsBy(query)
    }
}