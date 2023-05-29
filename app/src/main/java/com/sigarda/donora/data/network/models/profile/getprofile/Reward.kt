package com.sigarda.donora.data.network.models.profile.getprofile

data class Reward(
    val created_at: String,
    val description: String,
    val id: Int,
    val name: String,
    val point: String,
    val slug: String,
    val sponsor_profile_id: String,
    val updated_at: String
)