package com.devmark.devmark.data.mapper.workspace

import com.devmark.devmark.data.model.workspace.ResponseWorkSpaceInfoDTO
import com.devmark.devmark.domain.model.workspace.WorkSpaceInfoEntity

object WorkSpaceInfoMapper {
    fun mapperToResponseEntity(item: ResponseWorkSpaceInfoDTO): WorkSpaceInfoEntity {
        return item.run {
            val categoryList = mutableListOf<Pair<Int, String>>()
            val memberList = mutableListOf<Pair<Int, String>>()
            if (item.categories[0] != null) {
                item.categories.forEach {
                    categoryList.add(Pair(it.id, it.name))
                }
            }
            item.users.forEach {
                memberList.add(Pair(it.id, it.username))
            }
            WorkSpaceInfoEntity(categoryList, item.id, memberList)
        }
    }
}