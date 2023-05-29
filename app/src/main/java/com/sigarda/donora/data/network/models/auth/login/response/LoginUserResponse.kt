package com.sigarda.donora.data.network.models.auth.login.response

data class LoginUserResponse(
    val `data`: DataX,
    val message: String,
    val success: Boolean
)