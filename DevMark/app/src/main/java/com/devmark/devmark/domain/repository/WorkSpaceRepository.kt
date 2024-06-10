package com.devmark.devmark.domain.repository

import com.devmark.devmark.domain.model.bookmark.ResponseBookmarkCodeEntity
import com.devmark.devmark.domain.model.user.WorkspaceEntity
import com.devmark.devmark.domain.model.workspace.RecommendPostItem
import com.devmark.devmark.domain.model.workspace.RequestWorkSpaceCreateEntity
import com.devmark.devmark.domain.model.workspace.ResponseInviteCodeEntity
import com.devmark.devmark.domain.model.workspace.ResponseWorkspaceSettingInfoEntity
import com.devmark.devmark.domain.model.workspace.TopCategory
import com.devmark.devmark.domain.model.workspace.WorkSpaceInfoEntity

interface WorkSpaceRepository {
    suspend fun createWorkspace(
        accessToken: String,
        body: RequestWorkSpaceCreateEntity
    ): Result<WorkspaceEntity>

    suspend fun getInviteCode(
        accessToken: String,
        workspaceId: Int
    ): Result<ResponseInviteCodeEntity>

    suspend fun joinWorkspace(
        accessToken: String,
        inviteCode: String
    ): Result<Boolean>

    suspend fun getWorkspaceInfo(
        accessToken: String,
        workspaceId: Int
    ): Result<WorkSpaceInfoEntity>

    suspend fun deleteWorkspace(
        accessToken: String,
        workspaceId: Int
    ): Result<Boolean>

    suspend fun getBookmarkCode(
        accessToken: String,
        workspaceId: Int
    ): Result<ResponseBookmarkCodeEntity>

    suspend fun getWorkspaceSettingInfo(
        accessToken: String,
        workspaceId: Int
    ): Result<ResponseWorkspaceSettingInfoEntity>

    suspend fun getRecommendPost(
        accessToken: String,
        workspaceId: Int
    ): Result<List<RecommendPostItem>>

    suspend fun getTopCategory(
        accessToken: String,
        workspaceId: Int
    ): Result<List<TopCategory>>
}