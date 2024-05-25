package com.devmark.devmark.domain.model.bookmark


data class UpdateBookmarkEntity(
    val categoryId: Int,
    val userId: Int,
    val link: String,
    val summary: String,
    val workspaceId: Int,
    val title: String
)
