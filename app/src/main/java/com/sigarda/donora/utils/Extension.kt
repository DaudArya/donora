package com.sigarda.donora.utils

import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Extension {

    fun Date.toFormat(dateFormat: String): String {
        return SimpleDateFormat(dateFormat, Locale.getDefault()).format(this)
    }

    fun Context.loadImage(url: Any, into: ImageView) {
        Glide.with(this)
            .load(url)
            .into(into)
    }

    fun Context.showLongToast(message: String) {
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_LONG
        ).show()
    }
}