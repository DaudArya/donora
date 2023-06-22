package com.sigarda.donora.data.network.models.stock.vendor

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class VendorProfile(
    val address: String,
    val created_at: String,
    val id: Int,
    val name: String,
    val phone_number: String,
    val profile_picture: String,
    val slug: String,
    val updated_at: String,
    val user_id: String,
    val village_id: String
) : Parcelable