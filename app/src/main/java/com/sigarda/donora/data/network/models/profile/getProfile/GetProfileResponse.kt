package com.sigarda.donora.data.network.models.profile.getProfile

data class GetProfileResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
)