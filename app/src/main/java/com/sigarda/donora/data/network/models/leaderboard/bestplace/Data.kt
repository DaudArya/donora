package com.sigarda.donora.data.network.models.leaderboard.bestplace

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val NIK: String,
    val address: String,
    val age: Int,
    val blood_id: Int,
    val code_donor: String,
    val created_at: String,
    val gender: String,
    val id: Int,
    val name: String,
    val phone_number: String,
    val point: Int,
    val profile_picture: String,
    val slug: String,
    val updated_at: String,
    val user_id: Int,
    val village_id: Int
) : Parcelable