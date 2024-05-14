package com.devmark.devmark.data.mapper.workspace

import com.devmark.devmark.data.model.user.ResponseLoginDTO
import com.devmark.devmark.data.model.workspace.RequestWorkSpaceCreateDTO
import com.devmark.devmark.data.model.workspace.ResponseWorkSpaceCreateDTO
import com.devmark.devmark.domain.model.user.ResponseLoginEntity
import com.devmark.devmark.domain.model.workspace.RequestWorkSpaceCreateEntity
import com.devmark.devmark.domain.model.workspace.ResponseWorkSpaceCreateEntity

object WorkSpaceCreateMapper {
    fun mapperToResponseEntity(item: ResponseWorkSpaceCreateDTO): ResponseWorkSpaceCreateEntity {
        return item.run {
            ResponseWorkSpaceCreateEntity(bookmarkCount, description, id, name, userCount)
        }
    }

    fun mapperToRequestDTO(item: RequestWorkSpaceCreateEntity): RequestWorkSpaceCreateDTO {
        return item.run {
            RequestWorkSpaceCreateDTO(name, description)
        }
    }
}