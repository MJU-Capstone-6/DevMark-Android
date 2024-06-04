package com.devmark.devmark.data.mapper.workspace

import com.devmark.devmark.data.model.workspace.ResponseRecommendPostDTO
import com.devmark.devmark.domain.model.workspace.RecommendLink
import com.devmark.devmark.domain.model.workspace.RecommendPostItem

object RecommendPostMapper {
    fun mapperToResponseEntity(item: ResponseRecommendPostDTO): List<RecommendPostItem> {
        return item.map {
            RecommendPostItem(
                name = it.name, recommendLinks = it.recommendLinks.map { link ->
                    RecommendLink(
                        link.categoryId,
                        link.createdAt,
                        link.id,
                        link.link,
                        link.title,
                        link.updatedAt,
                        link.workspaceId
                    )
                }
            )
        }
    }
}