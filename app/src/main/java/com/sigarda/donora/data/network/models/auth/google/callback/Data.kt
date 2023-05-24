package com.sigarda.donora.data.network.models.auth.google.callback

data class Data(
    val token: String,
    val type: String,
    val user: User
)