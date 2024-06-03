package com.devmark.devmark.data.mapper.bookmark

import com.devmark.devmark.data.model.bookmark.ResponseBookmarkDTO
import com.devmark.devmark.domain.model.bookmark.BookmarkDetailEntity

object BookmarkDetailMapper {
    fun mapperToResponseEntity(item: ResponseBookmarkDTO): BookmarkDetailEntity {
        return item.run {
            BookmarkDetailEntity(
                id = this.bookmark.id,
                title = this.bookmark.title,
                summary = this.bookmark.summary,
                link = this.bookmark.link,
                categoryId = this.category.id,
                categoryName = this.category.name,
                workspaceId = this.workspace.id,
                isRead = this.bookmark.isRead
            )
        }
    }
}