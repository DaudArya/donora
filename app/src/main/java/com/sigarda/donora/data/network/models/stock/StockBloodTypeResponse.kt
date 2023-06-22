package com.sigarda.donora.data.network.models.stock

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class StockBloodTypeResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
)