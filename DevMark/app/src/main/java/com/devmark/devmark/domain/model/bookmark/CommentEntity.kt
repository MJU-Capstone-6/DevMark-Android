package com.devmark.devmark.domain.model.bookmark

data class CommentEntity(
    val id: Int,
    val bookmarkId: Int,
    val userId: Int,
    val userName: String,
    val context: String,
    val updatedAt: String
)
