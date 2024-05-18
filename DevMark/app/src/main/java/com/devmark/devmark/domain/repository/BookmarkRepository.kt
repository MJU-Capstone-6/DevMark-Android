package com.devmark.devmark.domain.repository

import com.devmark.devmark.domain.model.bookmark.BookmarkDetailEntity

interface BookmarkRepository {
    suspend fun getBookmarkDetail(accessToken: String, bookmarkId: Int): Result<BookmarkDetailEntity>
}