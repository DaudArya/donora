package com.sigarda.donora.data.network.models.schedule

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable


@Parcelize
data class VendorProfile(
    val address: String,
    val created_at: String,
    val id: Int,
    val name: String,
    val phone_number: String,
    val updated_at: String,
    val user_id: String,
    val village_id: String
) : Parcelable