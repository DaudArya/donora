package com.sigarda.donora.data.network.models.auth.login.response

data class LoginResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
)