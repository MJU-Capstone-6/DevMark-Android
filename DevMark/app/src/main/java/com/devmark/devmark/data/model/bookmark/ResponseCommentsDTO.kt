package com.devmark.devmark.data.model.bookmark

import com.google.gson.annotations.SerializedName

data class ResponseCommentsDTO(
    @SerializedName("bookmark_id")
    val bookmarkId: Int,
    @SerializedName("comment_context")
    val commentContext: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int
)
