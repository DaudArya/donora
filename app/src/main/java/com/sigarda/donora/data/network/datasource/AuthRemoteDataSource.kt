package com.sigarda.donora.data.network.datasource

import com.sigarda.donora.data.network.models.auth.create.CreateProfileRequestBody
import com.sigarda.donora.data.network.models.auth.create.CreateProfileResponse
import com.sigarda.donora.data.network.models.auth.google.login.GoogleAuthRequestBody
import com.sigarda.donora.data.network.models.auth.google.login.LoginGoogleResponse
import com.sigarda.donora.data.network.models.auth.login.requestbody.LoginRequestBody
import com.sigarda.donora.data.network.models.auth.login.response.LoginUserResponse
import com.sigarda.donora.data.network.models.auth.register.requestbody.RegisterRequestBody
import com.sigarda.donora.data.network.models.auth.register.response.RegisterResponse
import com.sigarda.donora.data.network.service.AuthApiInterface
import javax.inject.Inject

interface AuthRemoteDataSource {
    suspend fun postLogin(loginRequestBody: LoginRequestBody): LoginUserResponse
    suspend fun postRegister(registerRequestBody: RegisterRequestBody): RegisterResponse
    suspend fun postLoginGoogle(googleAuthRequestBody: GoogleAuthRequestBody): LoginGoogleResponse
    suspend fun postCreateUser(token : String, createUserRequestBody: CreateProfileRequestBody): CreateProfileResponse


}

class AuthRemoteDataSourceImpl @Inject constructor(private val apiService: AuthApiInterface) :
    AuthRemoteDataSource {

    override suspend fun postRegister(registerRequestBody: RegisterRequestBody): RegisterResponse {
        return apiService.postRegister(registerRequestBody)
    }

    override suspend fun postLoginGoogle(googleAuthRequestBody: GoogleAuthRequestBody): LoginGoogleResponse {
        return apiService.postLoginGoogle(googleAuthRequestBody)
    }

    override suspend fun postCreateUser(
        token: String,
        createUserRequestBody: CreateProfileRequestBody
    ): CreateProfileResponse {
        return apiService.postCreateUser(token,createUserRequestBody)
    }

    override suspend fun postLogin(loginRequestBody: LoginRequestBody): LoginUserResponse {
        return apiService.postLogin(loginRequestBody)
    }


}