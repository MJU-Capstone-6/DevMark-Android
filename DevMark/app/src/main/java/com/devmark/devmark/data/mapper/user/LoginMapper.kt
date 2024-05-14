package com.devmark.devmark.data.mapper.user

import com.devmark.devmark.data.model.user.ResponseLoginDTO
import com.devmark.devmark.domain.model.user.ResponseLoginEntity

object LoginMapper {
    fun mapperToResponseEntity(item: ResponseLoginDTO): ResponseLoginEntity {
        return item.run {
            ResponseLoginEntity(accessToken, refreshToken)
        }
    }
}