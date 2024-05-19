package com.devmark.devmark.data.model.common

import com.google.gson.annotations.SerializedName

data class OkResponse(
    @SerializedName("ok")
    val ok: Boolean
)
