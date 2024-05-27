package com.devmark.devmark.data.repository

import com.devmark.devmark.data.mapper.bookmark.BookmarkDetailMapper
import com.devmark.devmark.data.mapper.bookmark.BookmarksMapper
import com.devmark.devmark.data.mapper.bookmark.CommentMapper
import com.devmark.devmark.data.mapper.bookmark.UpdateBookmarkMapper
import com.devmark.devmark.data.remote.RetrofitClient
import com.devmark.devmark.data.remote.api.BookmarkService
import com.devmark.devmark.domain.model.bookmark.BookmarkDetailEntity
import com.devmark.devmark.domain.model.bookmark.BookmarksEntity
import com.devmark.devmark.domain.model.bookmark.CommentEntity
import com.devmark.devmark.domain.model.bookmark.UpdateBookmarkEntity
import com.devmark.devmark.domain.repository.BookmarkRepository
import org.json.JSONObject
import java.lang.Exception

class BookmarkRepositoryImpl : BookmarkRepository {
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
            if (response.body().isNullOrEmpty()) Result.success(listOf())
            else Result.success(CommentMapper.mapperToResponseEntity(response.body()!!))
        } else {
            val errorMsg = JSONObject(response.errorBody()!!.string()).getString("msg")
            Result.failure(Exception(errorMsg))
        }
    }

    override suspend fun getBookmarks(
        accessToken: String,
        workspaceId: Int,
        userIds: String,
        categoryIds: String
    ): Result<List<BookmarksEntity>> {
        val response =
            service.getBookmarks("Bearer $accessToken", workspaceId, userIds, categoryIds)
        return if (response.isSuccessful) {
            if (response.body().isNullOrEmpty()) Result.success(listOf())
            else Result.success(BookmarksMapper.mapperToResponseEntity(response.body()!!))
        } else {
            val errorMsg = JSONObject(response.errorBody()!!.string()).getString("msg")
            Result.failure(Exception(errorMsg))
        }
    }

    override suspend fun updateBookmark(
        accessToken: String,
        bookmarkId: Int,
        bookmark: UpdateBookmarkEntity
    ): Result<BookmarksEntity> {
        val response = service.updateBookmark(
            "Bearer $accessToken",
            bookmarkId,
            UpdateBookmarkMapper.mapperToRequestDto(bookmark)
        )
        return if (response.isSuccessful) {
            Result.success(UpdateBookmarkMapper.mapperToResponseEntity(response.body()!!))
        } else {
            val errorMsg = JSONObject(response.errorBody()!!.string()).getString("msg")
            Result.failure(Exception(errorMsg))
        }
    }
}