package com.devmark.devmark.domain.repository

import com.devmark.devmark.domain.model.bookmark.BookmarkDetailEntity
import com.devmark.devmark.domain.model.bookmark.BookmarksEntity
import com.devmark.devmark.domain.model.bookmark.CommentEntity
import com.devmark.devmark.domain.model.bookmark.RequestBookmarkEntity
import com.devmark.devmark.domain.model.bookmark.UpdateBookmarkEntity


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
    ): Result<UpdateBookmarkEntity>

    suspend fun addBookmark(
        accessToken: String,
        bookmark: RequestBookmarkEntity
    ): Result<UpdateBookmarkEntity>

    suspend fun deleteBookmark(
        accessToken: String,
        bookmarkId: Int,
    ): Result<Boolean>
}