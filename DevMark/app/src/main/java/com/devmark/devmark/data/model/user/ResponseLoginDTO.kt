package com.devmark.devmark.data.model.user

import com.google.gson.annotations.SerializedName

data class ResponseLoginDTO(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String
)
