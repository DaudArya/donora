package com.sigarda.donora.data.network.models.auth.google.callback

data class User(
    val created_at: String,
    val email: String,
    val google_id: String,
    val id: Int,
    val role_id: Int,
    val status: Int,
    val updated_at: String,
    val username: String
)