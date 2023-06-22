package com.sigarda.donora.data.network.models.history.Create

data class CreateHistoryResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
)