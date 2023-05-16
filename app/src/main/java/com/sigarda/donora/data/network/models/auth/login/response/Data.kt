package com.sigarda.donora.data.network.models.auth.login.response

data class Data(
    val token: String,
    val type: String,
    val user: User
)