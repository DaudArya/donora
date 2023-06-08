package com.sigarda.donora.data.network.models.dashboard.banner

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Data(
    val address: String,
    val blood_id: Int,
    val created_at: String,
    val description: String,
    val id: Int,
    val name: String,
    val necessity: String,
    val phone_number: String,
    val slug: String,
    val status: String,
    val updated_at: String,
    val village_id: Int
) : Parcelable