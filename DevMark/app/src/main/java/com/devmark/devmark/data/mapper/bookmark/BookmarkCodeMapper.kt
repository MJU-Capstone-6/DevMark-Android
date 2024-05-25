package com.devmark.devmark.data.mapper.bookmark

import com.devmark.devmark.data.model.bookmark.ResponseBookmarkCodeDTO
import com.devmark.devmark.domain.model.bookmark.ResponseBookmarkCodeEntity

object BookmarkCodeMapper {
    fun mapperToResponseEntity(item: ResponseBookmarkCodeDTO): ResponseBookmarkCodeEntity {
        return item.run {
            ResponseBookmarkCodeEntity(code = code)
        }
    }
}