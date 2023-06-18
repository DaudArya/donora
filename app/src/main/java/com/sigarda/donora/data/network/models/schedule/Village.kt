package com.sigarda.donora.data.network.models.schedule

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable


@Parcelize
data class Village(
    val created_at: String,
    val district: District,
    val district_id: String,
    val id: Int,
    val name: String,
    val updated_at: String
) : Parcelable