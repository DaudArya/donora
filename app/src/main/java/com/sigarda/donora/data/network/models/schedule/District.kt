package com.sigarda.donora.data.network.models.schedule

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable


@Parcelize
data class District(
    val city: City,
    val city_id: String,
    val created_at: String,
    val id: Int,
    val name: String,
    val updated_at: String
) : Parcelable