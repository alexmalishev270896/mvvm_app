package com.alex_malishev.data_layer.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.alex_malishev.data_layer.model.NewsDb
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface NewsDao {

    @Query("select * from news")
    fun getNews(): Single<List<NewsDb.NewsEntity>>

    @Insert
    fun saveNews(news: NewsDb.NewsEntity): Completable

    @Query("delete from news")
    fun deleteAllNews(): Completable

    @Query("delete from news where title=:title")
    fun deleteNews(title: String): Completable
}