package com.devmark.devmark.data.model.user


import com.google.gson.annotations.SerializedName

class ResponseNotificationDTO : ArrayList<ResponseNotificationDTOItem>()

data class ResponseNotificationDTOItem(
    @SerializedName("bookmarks")
    val bookmarks: List<Bookmark>,
    @SerializedName("notification_id")
    val notificationId: Int,
    @SerializedName("notification_title")
    val notificationTitle: String
)

data class Bookmark(
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_read")
    val isRead: Boolean,
    @SerializedName("link")
    val link: String,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("workspace_id")
    val workspaceId: Int
)