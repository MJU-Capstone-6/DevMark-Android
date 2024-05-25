package com.devmark.devmark.data.mapper.bookmark

import com.devmark.devmark.data.model.bookmark.ResponseCommentsDTO
import com.devmark.devmark.domain.model.bookmark.CommentEntity

object CommentMapper {
    fun mapperToResponseEntity(item: List<ResponseCommentsDTO>): List<CommentEntity> {
        return item.map {
            it.run {
                CommentEntity(
                    commentContext, commentId, createdAt, userId, username
                )
            }
        }
    }

}