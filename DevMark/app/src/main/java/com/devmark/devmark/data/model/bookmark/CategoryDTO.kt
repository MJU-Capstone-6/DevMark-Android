package com.devmark.devmark.data.model.bookmark


import com.google.gson.annotations.SerializedName

data class CategoryDTO(
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("updated_at") val updatedAt: String
)