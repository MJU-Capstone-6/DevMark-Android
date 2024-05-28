package com.devmark.devmark.data.mapper.bookmark

import com.devmark.devmark.data.model.bookmark.ResponseBookmarksDTO
import com.devmark.devmark.domain.model.bookmark.BookmarksEntity

object BookmarksMapper {
    fun mapperToResponseEntity(item: List<ResponseBookmarksDTO>): List<BookmarksEntity> {
        return item.map {
            BookmarksEntity(
                categoryId = it.categoryId, id = it.id, link = it.link,
                summary = it.summary ?: "",
                title = it.title,
                userId = it.userId,
                workspaceId = it.workspaceId,
                categoryName = it.categoryName,
                createAt = it.createdAt
            )
        }
    }
}