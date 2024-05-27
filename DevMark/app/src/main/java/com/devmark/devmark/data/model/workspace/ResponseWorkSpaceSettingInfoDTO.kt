package com.devmark.devmark.data.model.workspace


import com.google.gson.annotations.SerializedName

data class ResponseWorkSpaceSettingInfoDTO(
    @SerializedName("description")
    val description: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("user_bookmark_count")
    val userBookmarkCount: List<UserBookmarkCount>
)

data class UserBookmarkCount(
    @SerializedName("bookmark_count")
    val bookmarkCount: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("username")
    val username: String
)
