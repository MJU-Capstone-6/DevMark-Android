package com.devmark.devmark.domain.model.comment


data class CommentEntity(
    val commentContext: String,
    val commentId: Int,
    val createdAt: String,
    val userId: Int,
    val username: String
)