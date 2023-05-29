package com.sigarda.donora.data.network.models.profile.getprofile

data class GetProfileResponse(
    val `data`: List<Data>,
    val message: String,
    val success: Boolean
)