package com.alex_malishev.data_layer.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

sealed class NewsDb {

    @Entity(tableName = "news")
    data class NewsEntity(
        @PrimaryKey @ColumnInfo(name = "title") var title: String,
        var date: String,
        var author: String? = null,
        var description: String? = null,
        var url: String? = null,
        var imageUrl: String? = null,
        var content: String? = null,
        var sourceId: String? = null,
        var sourceName: String? = null
    ) : NewsDb()
}