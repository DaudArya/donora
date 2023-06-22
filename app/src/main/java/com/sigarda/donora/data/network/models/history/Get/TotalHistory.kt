package com.sigarda.donora.data.network.models.history.Get

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TotalHistory(
    val created_at: String,
    val date: String,
    val description: String,
    val id: Int,
    val receipt_donor: String,
    val status: String,
    val updated_at: String,
    val user_profile_id: String
) : Parcelable