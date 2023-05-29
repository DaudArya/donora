package com.sigarda.donora.data.network.models.profile.getprofile

data class UserReward(
    val created_at: String,
    val id: Int,
    val reward: Reward,
    val reward_id: String,
    val updated_at: String,
    val user_profile_id: String
)