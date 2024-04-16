package com.marwa.ar.data.model

import com.google.gson.annotations.SerializedName
import com.marwa.ar.domain.entity.ArticleEntity
import com.marwa.ar.domain.entity.NewsEntity


data class Articles(

    @SerializedName("source") var source: Source? = Source(),
    @SerializedName("author") var author: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("urlToImage") var urlToImage: String? = null,
    @SerializedName("publishedAt") var publishedAt: String? = null,
    @SerializedName("content") var content: String? = null,
    var isFavorite : Boolean = false

)
fun Articles.toArticleEntity(): ArticleEntity {
    return ArticleEntity(
        author = author ?: "",
        title = title ?: "",
        description = description ?: "",
        url = url,
        imageUrl = urlToImage,
        publishedAt = publishedAt ?: "",
    )
}

fun Articles.toNewEntity(): NewsEntity {
    return NewsEntity(
        author = author ?: "",
        title = title ?: "",
        description = description ?: "",
        url = url,
        imageUrl = urlToImage,
        publishedAt = publishedAt ?: "",
    )
}
