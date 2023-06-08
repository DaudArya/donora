package com.sigarda.donora.data.network.models.dashboard.banner

data class BannerResponse(
    val `data`: List<Data>,
    val message: String,
    val success: Boolean
)