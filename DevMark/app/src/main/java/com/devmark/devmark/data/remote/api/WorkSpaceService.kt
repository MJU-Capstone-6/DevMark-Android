package com.devmark.devmark.data.remote.api

import com.devmark.devmark.data.model.workspace.RequestWorkSpaceCreateDTO
import com.devmark.devmark.data.model.workspace.ResponseWorkSpaceCreateDTO
import com.devmark.devmark.data.model.workspace.ResponseWorkSpaceInfoDTO
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
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

    @GET("/api/v1/workspace/{id}")
    suspend fun getWorkspaceInfo(
        @Header("Authorization") accessToken: String,
        @Path("id") workspaceId: Int,
    ): Response<ResponseWorkSpaceInfoDTO>
}