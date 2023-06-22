package com.sigarda.donora.data.network.models.stock.vendorBlood

data class StockBlood(
    val created_at: String,
    val id: Int,
    val product_blood_id: String,
    val slug: Any,
    val stock: String,
    val updated_at: String,
    val vendor_profile: VendorProfile,
    val vendor_profile_id: String
)