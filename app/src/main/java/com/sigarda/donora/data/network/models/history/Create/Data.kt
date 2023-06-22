package com.sigarda.donora.data.network.models.history.Create

data class Data(
    val created_at: String,
    val date: String,
    val description: String,
    val id: Int,
    val receipt_donor: String,
    val slug: String,
    val status: String,
    val updated_at: String,
    val user_profile_id: Int
)