package com.devmark.devmark.data.model.bookmark


import com.google.gson.annotations.SerializedName

data class ResponseBookmarkDTO(
    @SerializedName("bookmark") val bookmark: BookmarkDTO,
    @SerializedName("category") val category: CategoryDTO,
    @SerializedName("workspace") val workspace: WorkspaceDTO
)