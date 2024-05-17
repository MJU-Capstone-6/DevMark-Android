package com.devmark.devmark.data.mapper.workspace

import com.devmark.devmark.data.model.workspace.RequestInviteCodeDTO
import com.devmark.devmark.data.model.workspace.ResponseInviteCodeDTO
import com.devmark.devmark.domain.model.workspace.ResponseInviteCodeEntity

object InviteCodeMapper {
    fun mapperToResponseEntity(item: ResponseInviteCodeDTO): ResponseInviteCodeEntity {
        return item.run {
            ResponseInviteCodeEntity(inviteCode = code)
        }
    }

    fun mapperToRequestDTO(item: Int): RequestInviteCodeDTO {
        return RequestInviteCodeDTO(item)

    }
}