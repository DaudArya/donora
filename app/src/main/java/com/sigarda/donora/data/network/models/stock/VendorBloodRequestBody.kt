package com.sigarda.donora.data.network.models.stock

import com.google.gson.annotations.SerializedName

data class VendorBloodRequestBody(
    @SerializedName("product_blood_id")
    val product_blood_id: String
)
