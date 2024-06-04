package com.devmark.devmark.domain.model.workspace

data class RecommendLink(
    val categoryId: Int,
    val createdAt: String,
    val id: Int,
    val link: String,
    val title: String,
    val updatedAt: String,
    val workspaceId: Int
)