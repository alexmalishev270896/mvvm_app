package com.alex_malishev.domain_layer.use_case.news

import com.alex_malishev.domain_layer.model.News
import com.alex_malishev.domain_layer.repository.news.INewsRepository
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(value = MockitoJUnitRunner::class)
class GetRecentNewsUseCaseTest {

    private val newsRepository: INewsRepository = mock(INewsRepository::class.java)

    private lateinit var getRecentNewsUseCase: GetRecentNewsUseCase

    @Before
    fun setUp() {
        getRecentNewsUseCase = GetRecentNewsUseCase(newsRepository)
    }

    @Test
    fun invokeSuccess() {
        val news = listOf(News("News", "12.12.2020"))
        `when`(newsRepository.getRecentNewsList()).thenReturn(Single.just(news))

        val list = getRecentNewsUseCase().test()

        verify(newsRepository, times(1)).getRecentNewsList()
        list.assertNoErrors()
        list.assertValue { it.isNotEmpty() }
        list.assertValue { it.size == news.size }
    }

    @Test
    fun invokeError() {
        `when`(newsRepository.getRecentNewsList()).thenReturn(Single.error(RuntimeException()))

        getRecentNewsUseCase().test()
            .assertError(RuntimeException::class.java)
        verify(newsRepository, times(1)).getRecentNewsList()

    }
}