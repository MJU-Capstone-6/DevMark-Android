package com.devmark.devmark.data.remote.api

import com.devmark.devmark.data.model.user.ResponseLoginDTO
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST

interface UserService {
    @POST("/api/v1/auth/kakao")
    suspend fun login(
        @Header("Authorization") accessToken: String
    ): Response<ResponseLoginDTO>
}