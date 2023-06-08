package com.sigarda.donora.data.network.models.dashboard.user.schedule

data class Data(
    val created_at: String,
    val date: String,
    val description: String,
    val id: Int,
    val profile_id: Int,
    val receipt_donor: String,
    val slug: Any,
    val status: Int,
    val updated_at: String
)