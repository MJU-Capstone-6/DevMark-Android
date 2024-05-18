package com.devmark.devmark.data.mapper.bookmark

import com.devmark.devmark.data.model.bookmark.ResponseBookmarkDetailDTO
import com.devmark.devmark.domain.model.bookmark.BookmarkDetailEntity

object BookmarkDetailMapper {
    fun mapperToResponseEntity(item: ResponseBookmarkDetailDTO): BookmarkDetailEntity {
        return item.run {
            BookmarkDetailEntity(id=id, link=link, summary=summary, categoryId=categoryId)
        }
    }
}