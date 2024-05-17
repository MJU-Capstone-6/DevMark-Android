package com.devmark.devmark.data.model.workspace

import com.google.gson.annotations.SerializedName

data class ResponseInviteCodeDTO(
    @SerializedName("code")
    val code: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("expired_at")
    val expiredAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("workspace_id")
    val workspaceId: Int
)