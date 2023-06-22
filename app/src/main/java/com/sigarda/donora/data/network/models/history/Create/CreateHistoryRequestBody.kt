package com.sigarda.donora.data.network.models.history.Create

import android.graphics.Bitmap
import java.io.File
import java.util.Date

data class CreateHistoryRequestBody(
    val receipt_donor: File,
    val description: String,
    val date: String
)
