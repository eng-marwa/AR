package com.marwa.ar.domain.entity

data class ArticleEntity (
    val title: String,
    val imageUrl: String?,
    val url: String?,
    val description: String,
    val author: String,
    val publishedAt: String,
    var isFavorite: Boolean = false
)