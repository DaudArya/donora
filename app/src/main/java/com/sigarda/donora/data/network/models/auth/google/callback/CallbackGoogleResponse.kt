package com.sigarda.donora.data.network.models.auth.google.callback

data class CallbackGoogleResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
)