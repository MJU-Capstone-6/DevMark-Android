package com.devmark.devmark.data.model.bookmark

import com.google.gson.annotations.SerializedName

data class ResponseCommentsDTO(
    @SerializedName("comment_context") val commentContext: String,
    @SerializedName("comment_id") val commentId: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("username") val username: String
)