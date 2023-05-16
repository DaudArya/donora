package com.sigarda.donora.data.network.models.auth.register.requestbody

import com.google.gson.annotations.SerializedName

data class RegisterRequestBody (
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("password")
    val password: String? = null
)
