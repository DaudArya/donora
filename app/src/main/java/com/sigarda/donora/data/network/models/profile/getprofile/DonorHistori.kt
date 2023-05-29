package com.sigarda.donora.data.network.models.profile.getprofile

data class DonorHistori(
    val created_at: String,
    val date: String,
    val description: String,
    val id: Int,
    val receipt_donor: String,
    val slug: Any,
    val status: String,
    val updated_at: String,
    val user_profile_id: String
)