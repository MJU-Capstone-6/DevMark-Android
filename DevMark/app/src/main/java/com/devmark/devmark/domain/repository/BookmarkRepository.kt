package com.devmark.devmark.domain.repository

import com.devmark.devmark.data.model.bookmark.BookmarkDTO
import com.devmark.devmark.data.model.bookmark.RequestUpdateBookmarkDTO
import com.devmark.devmark.domain.model.bookmark.BookmarkDetailEntity
import com.devmark.devmark.domain.model.bookmark.BookmarksEntity
import com.devmark.devmark.domain.model.bookmark.CommentEntity
import com.devmark.devmark.domain.model.bookmark.UpdateBookmarkEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Path

interface BookmarkRepository {
    suspend fun getBookmarkDetail(
        accessToken: String,
        bookmarkId: Int
    ): Result<BookmarkDetailEntity>

    suspend fun getComments(accessToken: String, bookmarkId: Int): Result<List<CommentEntity>>

    suspend fun getBookmarks(
        accessToken: String,
        workspaceId: Int,
        userIds: String,
        categoryIds: String
    ): Result<List<BookmarksEntity>>

    suspend fun updateBookmark(
        accessToken: String,
        bookmarkId: Int,
        bookmark: UpdateBookmarkEntity
    ): Result<BookmarksEntity>
}