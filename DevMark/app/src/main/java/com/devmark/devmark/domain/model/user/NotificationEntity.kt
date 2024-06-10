package com.devmark.devmark.domain.model.user

data class NotificationEntity(
    val bookmarks: List<Bookmark>
)

data class Bookmark(
    val bookmarkId: Int,
    val title: String,
)