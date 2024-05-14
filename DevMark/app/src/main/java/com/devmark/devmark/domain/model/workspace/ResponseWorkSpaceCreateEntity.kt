package com.devmark.devmark.domain.model.workspace


data class ResponseWorkSpaceCreateEntity(
    val bookmarkCount: Int,
    val description: String,
    val id: Int,
    val name: String,
    val userCount: Int
)
