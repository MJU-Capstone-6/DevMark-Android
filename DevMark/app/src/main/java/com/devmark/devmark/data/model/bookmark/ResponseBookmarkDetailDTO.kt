package com.devmark.devmark.data.model.bookmark

import com.google.gson.annotations.SerializedName

data class ResponseBookmarkDetailDTO(
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("link")
    val link: String,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("workspace_id")
    val workspaceId: Int
)
