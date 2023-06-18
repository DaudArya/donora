package com.sigarda.donora.data.network.models.stock

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ProductData(
    val product_Blood_id: Int,
    val product_name: String,
    val stock_product: Int
) : Parcelable