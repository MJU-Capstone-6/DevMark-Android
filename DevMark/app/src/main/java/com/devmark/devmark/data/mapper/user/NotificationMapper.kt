package com.devmark.devmark.data.mapper.user

import com.devmark.devmark.data.model.user.ResponseNotificationDTO
import com.devmark.devmark.domain.model.user.Bookmark
import com.devmark.devmark.domain.model.user.NotificationEntity

object NotificationMapper {
    fun mapperToResponseEntity(item: ResponseNotificationDTO): List<NotificationEntity> {
        return item.map { it ->
            NotificationEntity(
                bookmarks = it.bookmarks.map {
                    Bookmark(it.id, it.title)
                }
            )
        }
    }
}