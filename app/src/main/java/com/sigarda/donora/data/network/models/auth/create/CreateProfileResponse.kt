package com.sigarda.donora.data.network.models.auth.create

data class CreateProfileResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
)