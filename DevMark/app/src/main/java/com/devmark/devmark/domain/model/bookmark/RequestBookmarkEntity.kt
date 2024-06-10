package com.devmark.devmark.domain.model.bookmark


data class RequestBookmarkEntity(
    val categoryId: Int,
    val link: String,
    val summary: String,
    val title: String,
    val workspaceId: Int
)