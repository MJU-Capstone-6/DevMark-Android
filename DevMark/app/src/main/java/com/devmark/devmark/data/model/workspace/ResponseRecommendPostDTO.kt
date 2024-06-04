package com.devmark.devmark.data.model.workspace
import com.google.gson.annotations.SerializedName


class ResponseRecommendPostDTO : ArrayList<ResponseRecommendPostItemDTO>()

data class ResponseRecommendPostItemDTO(
    @SerializedName("name") val name: String,
    @SerializedName("recommend_links") val recommendLinks: List<RecommendLinkDTO>
)

data class RecommendLinkDTO(
    @SerializedName("category_id") val categoryId: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("id") val id: Int,
    @SerializedName("link") val link: String,
    @SerializedName("title") val title: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("workspace_id") val workspaceId: Int
)