package com.devmark.devmark.data.mapper.bookmark

import com.devmark.devmark.data.model.bookmark.BookmarkDTO
import com.devmark.devmark.data.model.bookmark.RequestUpdateBookmarkDTO
import com.devmark.devmark.domain.model.bookmark.UpdateBookmarkEntity

object UpdateBookmarkMapper {
    fun mapperToResponseEntity(item: BookmarkDTO): UpdateBookmarkEntity {
        return item.run {
            UpdateBookmarkEntity(
                categoryName?: "NONE", userId, link, summary, workspaceId, title
            )
        }
    }

    fun mapperToRequestDto(item: UpdateBookmarkEntity): RequestUpdateBookmarkDTO{
        return item.run {
            RequestUpdateBookmarkDTO(
                categoryName, userId, link, summary, workspaceId, title
            )
        }
    }
}