package com.devmark.devmark.data.mapper.workspace

import com.devmark.devmark.data.model.workspace.RequestWorkSpaceCreateDTO
import com.devmark.devmark.data.model.workspace.ResponseWorkSpaceCreateDTO
import com.devmark.devmark.domain.model.user.WorkspaceEntity
import com.devmark.devmark.domain.model.workspace.RequestWorkSpaceCreateEntity

object WorkSpaceCreateMapper {
    fun mapperToResponseEntity(item: ResponseWorkSpaceCreateDTO): WorkspaceEntity {
        return item.run {
            WorkspaceEntity(bookmarkCount, description, id, name, userCount)
        }
    }

    fun mapperToRequestDTO(item: RequestWorkSpaceCreateEntity): RequestWorkSpaceCreateDTO {
        return item.run {
            RequestWorkSpaceCreateDTO(name, description)
        }
    }
}