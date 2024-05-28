package com.devmark.devmark.data.model.bookmark

import com.google.gson.annotations.SerializedName

data class RequestUpdateBookmarkDTO(
    @SerializedName("category_name")
    val categoryName: String,
    @SerializedName("id")
    val userId: Int,
    @SerializedName("link")
    val link: String,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("workspace_id")
    val workspaceId: Int,
    @SerializedName("title")
    val title: String
)
