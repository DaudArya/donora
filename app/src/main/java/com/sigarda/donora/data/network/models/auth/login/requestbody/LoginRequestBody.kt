package com.sigarda.donora.data.network.models.auth.login.requestbody

import com.google.gson.annotations.SerializedName

data class LoginRequestBody
    (@SerializedName("username")
     val name: String? = null,
     @SerializedName("password")
     val password: String? = null
)
