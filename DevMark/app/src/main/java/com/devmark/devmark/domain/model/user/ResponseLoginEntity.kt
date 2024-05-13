package com.devmark.devmark.domain.model.user

data class ResponseLoginEntity(
    val accessToken: String,
    val refreshToken: String
)
