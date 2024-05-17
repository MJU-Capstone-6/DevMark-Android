package com.devmark.devmark.domain.model.workspace

import com.devmark.devmark.data.model.workspace.Category
import com.devmark.devmark.data.model.workspace.User

data class WorkSpaceInfoEntity(
    val categories: List<Pair<Int, String>>,
    val id: Int,
    val users: List<Pair<Int, String>>
)