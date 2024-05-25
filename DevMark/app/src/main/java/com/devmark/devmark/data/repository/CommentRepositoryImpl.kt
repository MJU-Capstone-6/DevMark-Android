package com.devmark.devmark.data.repository

import com.devmark.devmark.data.model.comment.RequestPostCommentDTO
import com.devmark.devmark.data.model.comment.RequestUpdateCommentDTO
import com.devmark.devmark.data.model.common.OkResponse
import com.devmark.devmark.data.remote.RetrofitClient
import com.devmark.devmark.data.remote.api.CommentService
import com.devmark.devmark.domain.model.comment.CommentEntity
import com.devmark.devmark.domain.repository.CommentRepository
import org.json.JSONObject
import java.lang.Exception

class CommentRepositoryImpl : CommentRepository {
    private val service = RetrofitClient.getInstance().create(CommentService::class.java)

    override suspend fun postComment(
        accessToken: String,
        bookmarkId: Int,
        context: String
    ): Result<CommentEntity> {
        val response = service.postComment("Bearer $accessToken", RequestPostCommentDTO(bookmarkId, context))
        return if (response.isSuccessful) {
            val item = response.body()!!.run {
                CommentEntity(
                    commentContext = commentContext,
                    commentId = this.id,
                    createdAt = this.createdAt,
                    userId = this.userId,
                    username = this.userId.toString()
                )
            }
            Result.success(item)
        } else {
            val errorMsg = JSONObject(response.errorBody()!!.string()).getString("msg")
            Result.failure(Exception(errorMsg))
        }
    }

    override suspend fun updateComment(
        accessToken: String,
        context: String,
        commentId: Int
    ): Result<CommentEntity> {
        val response = service.updateComment("Bearer $accessToken", RequestUpdateCommentDTO(context), commentId)
        return if (response.isSuccessful) {
            val item = response.body()!!.run {
                CommentEntity(
                    commentContext = commentContext,
                    commentId = this.id,
                    createdAt = this.createdAt,
                    userId = this.userId,
                    username = this.userId.toString()
                )
            }
            Result.success(item)
        } else {
            val errorMsg = JSONObject(response.errorBody()!!.string()).getString("msg")
            Result.failure(Exception(errorMsg))
        }
    }

    override suspend fun deleteComment(accessToken: String, commentId: Int): Result<Boolean> {
        val response = service.deleteComment("Bearer $accessToken", commentId)
        return if (response.isSuccessful) {
            Result.success(true)
        } else {
            val errorMsg = JSONObject(response.errorBody()!!.string()).getString("msg")
            Result.failure(Exception(errorMsg))
        }
    }
}