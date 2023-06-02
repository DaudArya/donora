package com.sigarda.donora.data.network.models.profile.getProfile

data class User(
    val created_at: String,
    val email: String,
    val email_verified_at: Any,
    val google_id: String,
    val id: Int,
    val role_id: String,
    val slug: Any,
    val status: String,
    val updated_at: String,
    val username: String
)