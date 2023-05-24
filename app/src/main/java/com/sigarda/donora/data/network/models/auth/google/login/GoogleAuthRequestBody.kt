package com.sigarda.donora.data.network.models.auth.google.login

import com.google.gson.annotations.SerializedName

data class GoogleAuthRequestBody
    (@SerializedName("id_token")
     val id_token: String? = null,
)
