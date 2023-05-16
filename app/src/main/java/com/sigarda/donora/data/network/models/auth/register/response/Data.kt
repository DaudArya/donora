package com.sigarda.donora.data.network.models.auth.register.response

data class Data(
    val created_at: String,
    val email: String,
    val id: Int,
    val role_id: Int,
    val slug: String,
    val status: Int,
    val updated_at: String,
    val username: String
)