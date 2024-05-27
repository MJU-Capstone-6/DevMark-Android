package com.devmark.devmark.data.remote.api

import com.devmark.devmark.data.model.bookmark.BookmarkDTO
import com.devmark.devmark.data.model.bookmark.RequestUpdateBookmarkDTO
import com.devmark.devmark.data.model.bookmark.ResponseBookmarkDTO
import com.devmark.devmark.data.model.bookmark.ResponseBookmarksDTO
import com.devmark.devmark.data.model.bookmark.ResponseCommentsDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface BookmarkService {
    @GET("/api/v1/bookmark/{id}")
    suspend fun getBookmarkDetail(
        @Header("Authorization") accessToken: String,
        @Path("id") bookmarkId: Int
    ): Response<ResponseBookmarkDTO>

    @GET("/api/v1/bookmark/{id}/comments")
    suspend fun getComments(
        @Header("Authorization") accessToken: String,
        @Path("id") bookmarkId: Int
    ): Response<List<ResponseCommentsDTO>>

    @GET("/api/v1/workspace/{id}/bookmark")
    suspend fun getBookmarks(
        @Header("Authorization") accessToken: String,
        @Path("id") workspaceId: Int,
        @Query("user") userIds: String,
        @Query("category") categoryIds: String
    ): Response<List<ResponseBookmarksDTO>>

    @PUT("/api/v1/bookmark/{id}")
    suspend fun updateBookmark(
        @Header("Authorization") accessToken: String,
        @Path("id") bookmarkId: Int,
        @Body bookmark: RequestUpdateBookmarkDTO
    ): Response<BookmarkDTO>
}