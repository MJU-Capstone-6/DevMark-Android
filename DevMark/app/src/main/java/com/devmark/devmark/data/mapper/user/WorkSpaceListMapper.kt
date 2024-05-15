package com.devmark.devmark.data.mapper.user

import com.devmark.devmark.data.model.workspace.ResponseWorkSpaceListDTO
import com.devmark.devmark.domain.model.user.ResponseWorkSpaceListEntity
import com.devmark.devmark.domain.model.user.WorkspaceEntity

object WorkSpaceListMapper {
    fun mapperToResponseEntity(item: ResponseWorkSpaceListDTO): ResponseWorkSpaceListEntity {
        return item.run {
            val workspaceList = mutableListOf<WorkspaceEntity>()
            item.workspaces.forEach {
                workspaceList.add(
                    WorkspaceEntity(
                        it.bookmarkCount,
                        it.description,
                        it.id,
                        it.name,
                        it.userCount
                    )
                )
            }
            ResponseWorkSpaceListEntity(item.id, workspaceList)
        }
    }
}