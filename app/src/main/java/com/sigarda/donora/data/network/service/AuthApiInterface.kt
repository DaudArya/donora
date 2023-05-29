package com.sigarda.donora.data.network.service

import com.sigarda.donora.data.network.models.auth.create.CreateProfileRequestBody
import com.sigarda.donora.data.network.models.auth.create.CreateProfileResponse
import com.sigarda.donora.data.network.models.auth.google.login.GoogleAuthRequestBody
import com.sigarda.donora.data.network.models.auth.google.login.LoginGoogleResponse
import com.sigarda.donora.data.network.models.auth.login.requestbody.LoginRequestBody
import com.sigarda.donora.data.network.models.auth.login.response.LoginUserResponse
import com.sigarda.donora.data.network.models.auth.register.requestbody.RegisterRequestBody
import com.sigarda.donora.data.network.models.auth.register.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiInterface {
    @POST("auth/register-user")
    suspend fun postRegister(
        @Body registerRequestBody: RegisterRequestBody
    ): RegisterResponse

    @POST("auth/login")
    suspend fun postLogin(
        @Body loginRequestBody: LoginRequestBody
    ): LoginUserResponse

    @POST("auth/google/login")
    suspend fun postLoginGoogle(
        @Body googleAuthRequestBody: GoogleAuthRequestBody
    ): LoginGoogleResponse

    @POST("user-profile/create")
    suspend fun postCreateUser(
        @Header("Authorization") token: String,
        @Body createUserRequestBody: CreateProfileRequestBody
    ): CreateProfileResponse


}