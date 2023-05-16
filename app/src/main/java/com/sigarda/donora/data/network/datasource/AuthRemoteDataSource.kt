package com.sigarda.donora.data.network.datasource

import com.sigarda.donora.data.network.models.auth.login.requestbody.LoginRequestBody
import com.sigarda.donora.data.network.models.auth.login.response.LoginResponse
import com.sigarda.donora.data.network.models.auth.register.requestbody.RegisterRequestBody
import com.sigarda.donora.data.network.models.auth.register.response.RegisterResponse
import com.sigarda.donora.data.network.service.AuthApiInterface
import javax.inject.Inject

interface AuthRemoteDataSource {
    suspend fun postLogin(loginRequestBody: LoginRequestBody): LoginResponse
    suspend fun postRegister(registerRequestBody: RegisterRequestBody): RegisterResponse
}

class AuthRemoteDataSourceImpl @Inject constructor(private val apiService: AuthApiInterface) :
    AuthRemoteDataSource {

    override suspend fun postRegister(registerRequestBody: RegisterRequestBody): RegisterResponse {
        return apiService.postRegister(registerRequestBody)
    }
    override suspend fun postLogin(loginRequestBody: LoginRequestBody): LoginResponse {
        return apiService.postLogin(loginRequestBody)
    }


}