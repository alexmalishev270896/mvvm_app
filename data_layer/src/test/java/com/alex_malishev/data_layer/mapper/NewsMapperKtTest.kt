package com.alex_malishev.data_layer.mapper

import com.alex_malishev.data_layer.model.NewsAPI
import com.alex_malishev.data_layer.model.NewsDb
import com.alex_malishev.domain_layer.model.News
import com.alex_malishev.domain_layer.model.NewsSource
import org.junit.Test

import org.junit.Assert.*

class NewsMapperKtTest {

    @Test
    fun testNewsResponseToDomainList() {
        val expected = News("title", "12.12.2020", source = NewsSource("id", "name"))

        val source = NewsAPI.NewsSourceResponse("id", "name")
        val articles = listOf(NewsAPI.NewsResponse(source, "title", "12.12.2020"))
        val result = NewsAPI.NewsListResponse("ok", articles).toDomainList()
        assert(result.isNotEmpty())
        assertEquals(listOf(expected).size, result.size)
        assertEquals(expected, result[0])
    }

    @Test
    fun testNewsResponseToDomain() {
        val expected = News("title", "12.12.2020", source = NewsSource("id", "name"))
        val source = NewsAPI.NewsSourceResponse("id", "name")
        val result = NewsAPI.NewsResponse(source, "title", "12.12.2020").toDomain()
        assertEquals(expected, result)
    }

    @Test
    fun testSourceResponseToDomain() {
        val expected = NewsSource("id", "name")
        val result = NewsAPI.NewsSourceResponse("id", "name").toDomain()
        assertEquals(expected, result)
    }

    @Test
    fun testToDbEntity() {
        val expected = NewsDb.NewsEntity("title", "12.12.2020", sourceId = "id", sourceName = "name")
        val result = News("title", "12.12.2020", source = NewsSource("id", "name")).toDbEntity()
        assertEquals(expected, result)
    }

    @Test
    fun testDbEntityToDomain() {
        val expected = News("title", "12.12.2020", source = NewsSource("id", "name"))
        val result = NewsDb.NewsEntity("title", "12.12.2020", sourceId = "id", sourceName = "name").toDomain()
        assertEquals(expected, result)
    }
}