package com.devmark.devmark.data.mapper.bookmark

import com.devmark.devmark.data.model.bookmark.BookmarkDTO
import com.devmark.devmark.data.model.bookmark.RequestUpdateBookmarkDTO
import com.devmark.devmark.domain.model.bookmark.BookmarksEntity
import com.devmark.devmark.domain.model.bookmark.UpdateBookmarkEntity

object UpdateBookmarkMapper {
    fun mapperToResponseEntity(item: BookmarkDTO): BookmarksEntity {
        return item.run {
            BookmarksEntity(
                categoryId, id, link, summary, title, userId, workspaceId, categoryName, createdAt
            )
        }
    }

    fun mapperToRequestDto(item: UpdateBookmarkEntity): RequestUpdateBookmarkDTO{
        return item.run {
            RequestUpdateBookmarkDTO(
                categoryId, userId, link, summary, workspaceId, title
            )
        }
    }
}