package com.devmark.devmark.data.model.workspace
import com.google.gson.annotations.SerializedName


class ResponseTopCategoryDTO : ArrayList<ResponseTopCategoryDTOItem>()

data class ResponseTopCategoryDTOItem(
    @SerializedName("bookmark_count") val bookmarkCount: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)