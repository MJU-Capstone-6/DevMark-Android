package com.devmark.devmark.domain.repository

import com.devmark.devmark.domain.model.user.NotificationEntity
import com.devmark.devmark.domain.model.user.ResponseLoginEntity
import com.devmark.devmark.domain.model.user.ResponseWorkSpaceListEntity

interface UserRepository {
    suspend fun login(accessToken: String, registrationToken: String): Result<ResponseLoginEntity>

    suspend fun getWorkspaceList(accessToken: String): Result<ResponseWorkSpaceListEntity>

    suspend fun getNotificationList(accessToken: String): Result<List<NotificationEntity>>
}