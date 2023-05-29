package com.sigarda.donora.data.network.models.profile.getprofile

data class Data(
    val NIK: String,
    val address: String,
    val age: String,
    val blood: Blood,
    val blood_id: String,
    val code_donor: String,
    val created_at: String,
    val donor_historis: List<DonorHistori>,
    val gender: String,
    val id: Int,
    val name: String,
    val phone_number: String,
    val point: String,
    val profile_picture: String,
    val slug: String,
    val updated_at: String,
    val user_id: String,
    val user_rewards: List<UserReward>,
    val village: Village,
    val village_id: String
)