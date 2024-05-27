package com.devmark.devmark.data.mapper.workspace

import com.devmark.devmark.data.model.workspace.ResponseWorkSpaceSettingInfoDTO
import com.devmark.devmark.domain.model.workspace.ResponseWorkspaceSettingInfoEntity
import com.devmark.devmark.domain.model.workspace.UserBookmarkCountEntity

object WorkSpaceSettingInfoMapper {
    fun mapperToResponseEntity(item: ResponseWorkSpaceSettingInfoDTO): ResponseWorkspaceSettingInfoEntity {
        return item.run {
            ResponseWorkspaceSettingInfoEntity(
                description,
                name,
                userBookmarkCount = this.userBookmarkCount.map {
                    UserBookmarkCountEntity(
                        it.bookmarkCount,
                        it.username
                    )
                }
            )
        }
    }
}