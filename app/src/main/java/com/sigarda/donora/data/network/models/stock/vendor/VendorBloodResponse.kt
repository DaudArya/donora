package com.sigarda.donora.data.network.models.stock.vendor

data class VendorBloodResponse(
    val `data`: List<Data>,
    val message: String,
    val success: Boolean
)