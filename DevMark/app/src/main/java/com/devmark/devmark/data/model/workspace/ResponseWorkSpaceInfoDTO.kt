package com.devmark.devmark.data.model.workspace


import com.google.gson.annotations.SerializedName

data class ResponseWorkSpaceInfoDTO(
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("users")
    val users: List<User>
)

data class Category(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("updated_at")
    val updatedAt: String
)

data class User(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("provider")
    val provider: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("username")
    val username: String
)