package com.sigarda.donora.data.network.models.auth.google.redirect

data class RedirectGoogleResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
)