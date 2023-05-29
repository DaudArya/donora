package com.sigarda.donora.data.network.models.auth.login.response

data class DataX(
    val token: String,
    val type: String,
    val user: UserX
)