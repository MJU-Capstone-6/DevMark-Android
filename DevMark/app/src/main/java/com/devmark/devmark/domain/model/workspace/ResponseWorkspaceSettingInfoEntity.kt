package com.devmark.devmark.domain.model.workspace

import com.devmark.devmark.data.model.workspace.UserBookmarkCount

data class ResponseWorkspaceSettingInfoEntity(
    val description: String,
    val name: String,
    val userBookmarkCount: List<UserBookmarkCountEntity>
)
