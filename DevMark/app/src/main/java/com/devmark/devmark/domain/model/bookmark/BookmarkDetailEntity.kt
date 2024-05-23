package com.devmark.devmark.domain.model.bookmark

data class BookmarkDetailEntity(
    val id: Int,
    val title: String,
    val link: String,
    val summary: String,
    val categoryId: Int,
    val categoryName: String
)