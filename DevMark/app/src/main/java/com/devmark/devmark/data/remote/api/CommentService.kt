package com.devmark.devmark.data.remote.api

import com.devmark.devmark.data.model.bookmark.BookmarkDTO
import com.devmark.devmark.data.model.bookmark.RequestUpdateBookmarkDTO
import com.devmark.devmark.data.model.bookmark.ResponseBookmarkDTO
import com.devmark.devmark.data.model.bookmark.ResponseBookmarksDTO
import com.devmark.devmark.data.model.bookmark.ResponseCommentsDTO
import com.devmark.devmark.data.model.comment.RequestPostCommentDTO
import com.devmark.devmark.data.model.comment.RequestUpdateCommentDTO
import com.devmark.devmark.data.model.comment.ResponsePostCommentDTO
import com.devmark.devmark.data.model.comment.ResponseUpdateCommentDTO
import com.devmark.devmark.data.model.common.OkResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentService {
    @POST("/api/v1/comment")
    suspend fun postComment(
        @Header("Authorization") accessToken: String,
        @Body body: RequestPostCommentDTO
    ): Response<ResponsePostCommentDTO>

    @PUT("/api/v1/comment/{id}")
    suspend fun updateComment(
        @Header("Authorization") accessToken: String,
        @Body body: RequestUpdateCommentDTO,
        @Path("id") commentId: Int
    ): Response<ResponseUpdateCommentDTO>

    @DELETE("/api/v1/comment/{id}")
    suspend fun deleteComment(
        @Header("Authorization") accessToken: String,
        @Path("id") commentId: Int
    ): Response<OkResponse>
}