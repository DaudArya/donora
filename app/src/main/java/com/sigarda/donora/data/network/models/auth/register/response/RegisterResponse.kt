package com.sigarda.donora.data.network.models.auth.register.response

data class RegisterResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
)