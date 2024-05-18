package com.devmark.devmark.domain.repository

import com.devmark.devmark.domain.model.bookmark.BookmarkDetailEntity
import com.devmark.devmark.domain.model.bookmark.CommentEntity

interface BookmarkRepository {
    suspend fun getBookmarkDetail(accessToken: String, bookmarkId: Int): Result<BookmarkDetailEntity>

    suspend fun getComments(accessToken: String, bookmarkId: Int): Result<List<CommentEntity>>
}