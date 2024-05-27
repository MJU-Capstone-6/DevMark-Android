package com.devmark.devmark.domain.model.bookmark


data class BookmarksEntity(
    val categoryId: Int,
    val id: Int,
    val link: String,
    val summary: String,
    val title: String,
    val userId: Int,
    val workspaceId: Int,
    val categoryName: String
)
