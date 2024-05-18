package com.devmark.devmark.data.remote.api

import com.devmark.devmark.data.model.workspace.RequestInviteCodeDTO
import com.devmark.devmark.data.model.workspace.RequestJoinWorkSpaceDTO
import com.devmark.devmark.data.model.workspace.RequestWorkSpaceCreateDTO
import com.devmark.devmark.data.model.workspace.ResponseInviteCodeDTO
import com.devmark.devmark.data.model.workspace.ResponseJoinWorkSpaceDTO
import com.devmark.devmark.data.model.workspace.ResponseWorkSpaceCreateDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface WorkSpaceService {
    @POST("/api/v1/workspace")
    suspend fun createWorkspace(
        @Header("Authorization") accessToken: String,
        @Body body: RequestWorkSpaceCreateDTO
    ): Response<ResponseWorkSpaceCreateDTO>

    @POST("/api/v1/invitecode")
    suspend fun getInviteCode(
        @Header("Authorization") accessToken: String,
        @Body workspaceId: RequestInviteCodeDTO
    ): Response<ResponseInviteCodeDTO>

    @POST("/api/v1/workspace/join")
    suspend fun joinWorkspace(
        @Header("Authorization") accessToken: String,
        @Body inviteCode: RequestJoinWorkSpaceDTO
    ): Response<ResponseJoinWorkSpaceDTO>
}