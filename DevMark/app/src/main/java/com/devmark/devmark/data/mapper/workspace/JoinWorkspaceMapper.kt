package com.devmark.devmark.data.mapper.workspace

import com.devmark.devmark.data.model.workspace.RequestJoinWorkSpaceDTO
import com.devmark.devmark.data.model.workspace.ResponseJoinWorkSpaceDTO
import com.devmark.devmark.domain.model.user.WorkspaceEntity

object JoinWorkspaceMapper {
    fun mapperToResponseEntity(item: ResponseJoinWorkSpaceDTO): WorkspaceEntity {
        return item.run {
            WorkspaceEntity(bookmarkCount, description, id, name, userCount)
        }
    }

    fun mapperToRequestDTO(item: String): RequestJoinWorkSpaceDTO {
        return RequestJoinWorkSpaceDTO(item)
    }
}