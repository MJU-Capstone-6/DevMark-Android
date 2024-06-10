package com.devmark.devmark.domain.model.workspace


data class WorkSpaceInfoEntity(
    val categories: List<Pair<Int, String>>,
    val id: Int,
    val users: List<Pair<Int, String>>
)