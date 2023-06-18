package com.sigarda.donora.data.network.models.stock

data class Data(
    val blood: Blood,
    val product_data: List<ProductData>,
    val total_stock: Int
)