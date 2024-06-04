package com.devmark.devmark.data.remote.api

import com.devmark.devmark.data.model.bookmark.ResponseBookmarkCodeDTO
import com.devmark.devmark.data.model.common.OkResponse
import com.devmark.devmark.data.model.workspace.RequestInviteCodeDTO
import com.devmark.devmark.data.model.workspace.RequestJoinWorkSpaceDTO
import com.devmark.devmark.data.model.workspace.RequestWorkSpaceCreateDTO
import com.devmark.devmark.data.model.workspace.ResponseInviteCodeDTO
import com.devmark.devmark.data.model.workspace.ResponseJoinWorkSpaceDTO
import com.devmark.devmark.data.model.workspace.ResponseRecommendPostDTO
import com.devmark.devmark.data.model.workspace.ResponseWorkSpaceCreateDTO
import com.devmark.devmark.data.model.workspace.ResponseWorkSpaceInfoDTO
import com.devmark.devmark.data.model.workspace.ResponseWorkSpaceSettingInfoDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

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
    ): Response<OkResponse>
    
    @GET("/api/v1/workspace/{id}")
    suspend fun getWorkspaceInfo(
        @Header("Authorization") accessToken: String,
        @Path("id") workspaceId: Int,
    ): Response<ResponseWorkSpaceInfoDTO>

    @DELETE("/api/v1/workspace/{id}")
    suspend fun deleteWorkspace(
        @Header("Authorization") accessToken: String,
        @Path("id") workspaceId: Int
    ): Response<OkResponse>

    @POST("/api/v1/workspace/{id}/code")
    suspend fun getBookmarkCode(
        @Header("Authorization") accessToken: String,
        @Path("id") workspaceId: Int
    ): Response<ResponseBookmarkCodeDTO>

    @GET("/api/v1/workspace/{id}/info")
    suspend fun getWorkspaceSettingInfo(
        @Header("Authorization") accessToken: String,
        @Path("id") workspaceId: Int
    ): Response<ResponseWorkSpaceSettingInfoDTO>

    @GET("/api/v1/workspace/{id}/recommend")
    suspend fun getRecommendPost(
        @Header("Authorization") accessToken: String,
        @Path("id") workspaceId: Int
    ): Response<ResponseRecommendPostDTO>
}