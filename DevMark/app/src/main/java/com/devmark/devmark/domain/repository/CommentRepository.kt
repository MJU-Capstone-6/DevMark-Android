package com.devmark.devmark.domain.repository

import com.devmark.devmark.domain.model.comment.CommentEntity

interface CommentRepository {
    suspend fun postComment(
        accessToken: String,
        bookmarkId: Int,
        context: String
    ): Result<CommentEntity>

    suspend fun updateComment(
        accessToken: String,
        context: String,
        commentId: Int
    ): Result<CommentEntity>

    suspend fun deleteComment(
        accessToken: String,
        commentId: Int
    ): Result<Boolean>
}