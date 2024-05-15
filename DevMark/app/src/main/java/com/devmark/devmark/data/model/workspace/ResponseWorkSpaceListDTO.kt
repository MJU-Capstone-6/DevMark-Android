package com.devmark.devmark.data.model.workspace


import com.google.gson.annotations.SerializedName

data class ResponseWorkSpaceListDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("workspaces")
    val workspaces: List<WorkspaceDTO>
)

data class WorkspaceDTO(
    @SerializedName("bookmark_count")
    val bookmarkCount: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_count")
    val userCount: Int
)

