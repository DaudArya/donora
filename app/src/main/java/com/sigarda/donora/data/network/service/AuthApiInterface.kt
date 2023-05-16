package com.sigarda.donora.data.network.service

import com.sigarda.donora.data.network.models.auth.login.requestbody.LoginRequestBody
import com.sigarda.donora.data.network.models.auth.login.response.LoginResponse
import com.sigarda.donora.data.network.models.auth.register.requestbody.RegisterRequestBody
import com.sigarda.donora.data.network.models.auth.register.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiInterface {
    @POST("auth/register-user")
    suspend fun postRegister(
        @Body registerRequestBody: RegisterRequestBody
    ): RegisterResponse

    @POST("auth/login")
    suspend fun postLogin(
        @Body loginRequestBody: LoginRequestBody
    ): LoginResponse
}