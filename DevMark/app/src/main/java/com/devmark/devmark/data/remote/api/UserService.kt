package com.devmark.devmark.data.remote.api

import com.devmark.devmark.data.model.user.RequestLoginDTO
import com.devmark.devmark.data.model.user.ResponseLoginDTO
import com.devmark.devmark.data.model.user.ResponseNotificationDTO
import com.devmark.devmark.data.model.workspace.ResponseWorkSpaceListDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserService {
    @POST("/api/v1/auth/kakao")
    suspend fun login(
        @Header("Authorization") accessToken: String,
        @Body registrationToken: RequestLoginDTO
    ): Response<ResponseLoginDTO>

    @GET("/api/v1/user/workspace")
    suspend fun getWorkspaceList(
        @Header("Authorization") accessToken: String,
    ): Response<ResponseWorkSpaceListDTO>

    @GET("/api/v1/user/notification")
    suspend fun getNotificationList(
        @Header("Authorization") accessToken: String,
    ): Response<ResponseNotificationDTO>
}