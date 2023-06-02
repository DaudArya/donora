package com.sigarda.donora.data.network.models.profile.getProfile

data class Data(
    val NIK: Any,
    val address: Any,
    val age: Any,
    val blood: Any,
    val blood_id: Any,
    val code_donor: Any,
    val created_at: String,
    val donor_historis: List<Any>,
    val gender: Any,
    val id: Int,
    val name: Any,
    val phone_number: Any,
    val point: Any,
    val profile_picture: Any,
    val slug: String,
    val updated_at: String,
    val user: User,
    val user_id: String,
    val user_rewards: List<Any>,
    val village: Any,
    val village_id: Any
)