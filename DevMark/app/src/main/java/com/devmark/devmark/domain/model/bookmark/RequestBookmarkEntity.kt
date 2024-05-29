package com.devmark.devmark.domain.model.bookmark


import com.google.gson.annotations.SerializedName

data class RequestBookmarkEntity(
    val categoryId: Int,
    val link: String,
    val summary: String,
    val title: String,
    val workspaceId: Int
)