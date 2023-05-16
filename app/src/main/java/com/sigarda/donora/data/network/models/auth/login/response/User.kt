package com.sigarda.donora.data.network.models.auth.login.response

data class User(
    val created_at: String,
    val email: String,
    val email_verified_at: Any,
    val google_id: Any,
    val id: Int,
    val role: Role,
    val role_id: String,
    val slug: String,
    val status: String,
    val updated_at: String,
    val user_profile: Any,
    val username: String
)