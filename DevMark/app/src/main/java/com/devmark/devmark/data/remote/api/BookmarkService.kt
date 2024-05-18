package com.devmark.devmark.data.remote.api

import com.devmark.devmark.data.model.bookmark.ResponseBookmarkDetailDTO
import com.devmark.devmark.data.model.bookmark.ResponseCommentsDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface BookmarkService {
    @GET("/api/v1/bookmark/{id}")
    suspend fun getBookmarkDetail(
        @Header("Authorization") accessToken: String,
        @Path("id") bookmarkId: Int
    ): Response<ResponseBookmarkDetailDTO>

    @GET("/api/v1/bookmark/{id}/comments")
    suspend fun getComments(
        @Header("Authorization") accessToken: String,
        @Path("id") bookmarkId: Int
    ): Response<List<ResponseCommentsDTO>>
}