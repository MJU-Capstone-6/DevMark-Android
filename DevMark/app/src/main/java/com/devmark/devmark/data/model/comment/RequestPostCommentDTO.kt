package com.devmark.devmark.data.model.comment

import com.google.gson.annotations.SerializedName

data class RequestPostCommentDTO(
    @SerializedName("bookmark_id")
    val bookmarkId: Int,
    @SerializedName("context")
    val context: String
)
