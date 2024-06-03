package com.devmark.devmark.domain.model.bookmark

data class BookmarkDetailEntity(
    val id: Int,
    val title: String,
    val link: String,
    val summary: String?,
    var categoryId: Int,
    var categoryName: String,
    val workspaceId: Int,
    val isRead: Boolean
)