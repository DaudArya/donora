package com.sigarda.donora.data.network.models.schedule



data class ScheduleDonorResponse(
    val `data`: List<Data>,
    val message: String,
    val success: Boolean
)