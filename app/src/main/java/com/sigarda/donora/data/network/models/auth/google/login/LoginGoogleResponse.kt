package com.sigarda.donora.data.network.models.auth.google.login

data class LoginGoogleResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
)