package com.devmark.devmark.data.model.bookmark


import com.google.gson.annotations.SerializedName

data class BookmarkDTO(
    @SerializedName("category_id") val categoryId: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("id") val id: Int,
    @SerializedName("link") val link: String,
    @SerializedName("summary") val summary: String,
    @SerializedName("title") val title: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("workspace_id") val workspaceId: Int,
    @SerializedName("category_name") val categoryName: String?,
    @SerializedName("is_read") val isRead: Boolean
)