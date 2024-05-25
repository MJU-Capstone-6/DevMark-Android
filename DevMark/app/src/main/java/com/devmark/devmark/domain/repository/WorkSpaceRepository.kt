package com.devmark.devmark.domain.repository

import com.devmark.devmark.data.model.common.OkResponse
import com.devmark.devmark.domain.model.user.WorkspaceEntity
import com.devmark.devmark.domain.model.workspace.RequestWorkSpaceCreateEntity
import com.devmark.devmark.domain.model.workspace.ResponseInviteCodeEntity
import com.devmark.devmark.domain.model.workspace.WorkSpaceInfoEntity
import retrofit2.http.Header
import retrofit2.http.Path

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
        @Header("Authorization") accessToken: String,
        @Path("id") workspaceId: Int
    ): Result<Boolean>
}