package com.devmark.devmark.domain.repository

import com.devmark.devmark.domain.model.user.WorkspaceEntity
import com.devmark.devmark.domain.model.workspace.RequestWorkSpaceCreateEntity
import com.devmark.devmark.domain.model.workspace.ResponseInviteCodeEntity

interface WorkSpaceRepository {
    suspend fun createWorkspace(
        accessToken: String,
        body: RequestWorkSpaceCreateEntity
    ): Result<WorkspaceEntity>

    suspend fun getInviteCode(
        accessToken: String,
        workspaceId: Int
    ): Result<ResponseInviteCodeEntity>
}