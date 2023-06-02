package com.sigarda.donora.data.network.models.profile.profile

import com.google.gson.annotations.SerializedName

data class GetprofileRequestBody(
    @SerializedName("id")
    val id: Int?,
)
