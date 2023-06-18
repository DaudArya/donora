package com.sigarda.donora.data.network.models.schedule

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable


@Parcelize
data class City(
    val created_at: String,
    val id: Int,
    val name: String,
    val province_id: String,
    val updated_at: String
) : Parcelable