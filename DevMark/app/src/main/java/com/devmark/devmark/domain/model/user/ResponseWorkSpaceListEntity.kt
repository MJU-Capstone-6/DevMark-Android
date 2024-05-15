package com.devmark.devmark.domain.model.user

data class ResponseWorkSpaceListEntity(
    val id: Int,
    val workspaces: List<WorkspaceEntity>
)

data class WorkspaceEntity(
    val bookmarkCount: Int,
    val description: String,
    val id: Int,
    val name: String,
    val userCount: Int
)