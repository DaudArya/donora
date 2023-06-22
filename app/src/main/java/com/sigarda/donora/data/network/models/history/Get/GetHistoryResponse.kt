package com.sigarda.donora.data.network.models.history.Get

data class GetHistoryResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
)