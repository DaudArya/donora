package com.sigarda.donora.data.network.models.schedule

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val address: String,
    val created_at: String,
    val id: Int,
    val slug: String,
    val time_end: String,
    val time_start: String,
    val updated_at: String,
    val vendor_id: Int,
    val village_id: Int
) : Parcelable