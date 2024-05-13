package com.devmark.devmark.domain.repository

import com.devmark.devmark.domain.model.user.ResponseLoginEntity

interface UserRepository {
    suspend fun login(accessToken: String): Result<ResponseLoginEntity>
}