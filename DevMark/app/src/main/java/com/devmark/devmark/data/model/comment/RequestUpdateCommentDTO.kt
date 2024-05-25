package com.devmark.devmark.data.model.comment

import com.google.gson.annotations.SerializedName

data class RequestUpdateCommentDTO(
    @SerializedName("comment_context")
    val comment: String
)
