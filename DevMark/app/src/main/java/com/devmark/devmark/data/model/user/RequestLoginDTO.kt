package com.devmark.devmark.data.model.user

import com.google.gson.annotations.SerializedName

data class RequestLoginDTO(
    @SerializedName("registration_token")
    val registrationToken: String
)
