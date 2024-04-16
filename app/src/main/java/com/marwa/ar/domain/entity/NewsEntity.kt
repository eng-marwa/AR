package com.marwa.ar.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.marwa.ar.data.model.Articles

@Entity(tableName = "news_table")
data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo
    val title: String,
    @ColumnInfo
    val imageUrl: String?,
    @ColumnInfo
    val url: String?,
    @ColumnInfo
    val description: String,
    @ColumnInfo
    val author: String,
    @ColumnInfo
    val publishedAt: String,

    ) {
    @Ignore
    var isFavorite: Boolean = false
}

fun NewsEntity.toArticle(): Articles {
    return Articles(
        title = title,
        url = url,
        urlToImage = imageUrl,
        description = description,
        author = author,
        publishedAt = publishedAt
    )
}


