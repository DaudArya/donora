package com.sigarda.donora.data.network.models.auth.google.login

data class User(
    val created_at: String,
    val email: String,
    val email_verified_at: Any,
    val google_id: String,
    val id: Int,
    val role_id: Int,
    val slug: Any,
    val status: Int,
    val updated_at: String,
    val username: String
)