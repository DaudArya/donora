package com.sigarda.donora.data.network.models.auth.create

import com.google.gson.annotations.SerializedName

data class CreateProfileRequestBody (
    @SerializedName("id")
    val id: Int? = null
)