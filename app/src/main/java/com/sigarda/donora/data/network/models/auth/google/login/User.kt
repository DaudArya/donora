package com.sigarda.donora.data.network.models.auth.google.login

import com.sigarda.donora.data.network.models.auth.login.response.UserProfile

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
    val user_profile: UserProfile,
    val username: String
)