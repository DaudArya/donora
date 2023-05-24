package com.sigarda.donora.data.network.models.auth.google.login

data class Data(
    val token: String,
    val type: String,
    val user: User
)