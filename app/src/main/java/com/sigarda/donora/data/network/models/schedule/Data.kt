package com.sigarda.donora.data.network.models.schedule

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable


@Parcelize
data class Data(
    val address: String,
    val created_at: String,
    val id: Int,
    val time_end: String,
    val time_start: String,
    val updated_at: String,
    val vendor_profile: VendorProfile,
    val vendor_profile_id: String,
    val village: Village,
    val village_id: String
) : Parcelable