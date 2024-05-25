package com.devmark.devmark.domain.model.comment

import com.google.gson.annotations.SerializedName

data class CommentEntity (
    val bookmarkId: Int,
    val commentContext: String,
    val createdAt: String,
    val id: Int,
    val updatedAt: String,
    val userId: Int
)