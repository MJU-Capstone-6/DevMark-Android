package com.devmark.devmark.data.repository

import com.devmark.devmark.data.mapper.bookmark.BookmarkDetailMapper
import com.devmark.devmark.data.mapper.bookmark.CommentMapper
import com.devmark.devmark.data.remote.RetrofitClient
import com.devmark.devmark.data.remote.api.BookmarkService
import com.devmark.devmark.domain.model.bookmark.BookmarkDetailEntity
import com.devmark.devmark.domain.model.bookmark.CommentEntity
import com.devmark.devmark.domain.repository.BookmarkRepository
import org.json.JSONObject
import java.lang.Exception

class BookmarkRepositoryImpl: BookmarkRepository {
    private val service = RetrofitClient.getInstance().create(BookmarkService::class.java)

    override suspend fun getBookmarkDetail(
        accessToken: String,
        bookmarkId: Int
    ): Result<BookmarkDetailEntity> {
        val response = service.getBookmarkDetail("Bearer $accessToken", bookmarkId)
        return if (response.isSuccessful) {
            Result.success(BookmarkDetailMapper.mapperToResponseEntity(response.body()!!))
        } else {
            val errorMsg = JSONObject(response.errorBody()!!.string()).getString("msg")
            Result.failure(Exception(errorMsg))
        }
    }

    override suspend fun getComments(
        accessToken: String,
        bookmarkId: Int
    ): Result<List<CommentEntity>> {
        val response = service.getComments("Bearer $accessToken", bookmarkId)
        return if (response.isSuccessful) {
            Result.success(CommentMapper.mapperToResponseEntity(response.body()!!))
        } else {
            val errorMsg = JSONObject(response.errorBody()!!.string()).getString("msg")
            Result.failure(Exception(errorMsg))
        }
    }
}