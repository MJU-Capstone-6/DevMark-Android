package com.devmark.devmark.data.model.bookmark


import com.google.gson.annotations.SerializedName

data class ResponseBookmarkCodeDTO(
    @SerializedName("code")
    val code: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("workspace_id")
    val workspaceId: Int
)