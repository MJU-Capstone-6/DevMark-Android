package com.devmark.devmark.data.mapper.workspace

import com.devmark.devmark.data.model.workspace.ResponseRecommendPostDTO
import com.devmark.devmark.data.model.workspace.ResponseTopCategoryDTO
import com.devmark.devmark.domain.model.workspace.RecommendLink
import com.devmark.devmark.domain.model.workspace.RecommendPostItem
import com.devmark.devmark.domain.model.workspace.TopCategory

object TopCategoryMapper {
    fun mapperToResponseEntity(item: ResponseTopCategoryDTO): List<TopCategory> {
        return item.map {
           TopCategory(
               bookmarkCount = it.bookmarkCount,
               id = it.id,
               name = it.name
           )
        }
    }
}