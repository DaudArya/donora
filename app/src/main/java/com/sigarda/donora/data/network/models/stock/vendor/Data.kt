package com.sigarda.donora.data.network.models.stock.vendor

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Data(
    val id: Int,
    val product_blood_id: String,
    val stock: String,
    val slug: String,
    val created_at: String,
    val updated_at: String,
    val vendor_profile: VendorProfile,
    val vendor_profile_id: String
) : Parcelable