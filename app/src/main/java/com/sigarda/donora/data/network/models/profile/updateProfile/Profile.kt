package com.sigarda.donora.data.network.models.profile.updateProfile

data class Profile(
    val NIK: String,
    val address: String,
    val age: String,
    val blood_id: String,
    val code_donor: String,
    val created_at: String,
    val gender: String,
    val id: Int,
    val name: String,
    val phone_number: String,
    val point: Any,
    val profile_picture: String,
    val slug: String,
    val updated_at: String,
    val user_id: String,
    val village_id: String
)