package com.devmark.devmark.data.mapper.bookmark

import com.devmark.devmark.data.model.bookmark.ResponseCommentsDTO
import com.devmark.devmark.domain.model.bookmark.CommentEntity

object CommentMapper {
    fun mapperToResponseEntity(item: List<ResponseCommentsDTO>): List<CommentEntity> {
        // todo API 수정되면 반영해야 함
        return item.map {
            CommentEntity(
                id = it.id,
                bookmarkId = it.bookmarkId,
                userId = it.userId,
                userName = "", // 빈 문자열로 설정된 userName
                context = it.commentContext,
                updatedAt = it.updatedAt
            )
        }
    }

}