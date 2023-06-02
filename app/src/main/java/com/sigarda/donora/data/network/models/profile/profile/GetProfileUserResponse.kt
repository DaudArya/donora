package com.sigarda.donora.data.network.models.profile.profile

import com.google.gson.annotations.SerializedName

data class GetProfileUserResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)