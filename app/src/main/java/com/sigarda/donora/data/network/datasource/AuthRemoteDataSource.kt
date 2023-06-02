package com.sigarda.donora.data.network.datasource

import com.sigarda.donora.data.network.models.auth.create.CreateProfileRequestBody
import com.sigarda.donora.data.network.models.auth.create.CreateProfileResponse
import com.sigarda.donora.data.network.models.auth.google.login.GoogleAuthRequestBody
import com.sigarda.donora.data.network.models.auth.google.login.LoginGoogleResponse
import com.sigarda.donora.data.network.models.auth.login.requestbody.LoginRequestBody
import com.sigarda.donora.data.network.models.auth.login.response.LoginUserResponse
import com.sigarda.donora.data.network.models.auth.register.requestbody.RegisterRequestBody
import com.sigarda.donora.data.network.models.auth.register.response.RegisterResponse
import com.sigarda.donora.data.network.models.profile.getProfile.GetProfileResponse
import com.sigarda.donora.data.network.models.profile.profile.GetProfileUserResponse
import com.sigarda.donora.data.network.models.profile.updateProfile.UpdateProfileResponse
import com.sigarda.donora.data.network.service.AuthApiInterface
import com.squareup.okhttp.RequestBody
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Part
import javax.inject.Inject

interface AuthRemoteDataSource {
    suspend fun postLogin(loginRequestBody: LoginRequestBody): LoginUserResponse
    suspend fun postRegister(registerRequestBody: RegisterRequestBody): RegisterResponse
    suspend fun postLoginGoogle(googleAuthRequestBody: GoogleAuthRequestBody): LoginGoogleResponse
    suspend fun postCreateUser(token : String, createUserRequestBody: CreateProfileRequestBody): CreateProfileResponse
    suspend fun getProfile(token : String): GetProfileResponse
    suspend fun updateProfile(token: String): UpdateProfileResponse

//    suspend fun putProfile(
//        username : RequestBody,
//        fullName : RequestBody,
//        blood_id : RequestBody ,
//        village_id : RequestBody,
//        nik : RequestBody,
//        gender : RequestBody,
//        phone_number : RequestBody,
//        donor_code : RequestBody,
//        age : RequestBody,
//        address : RequestBody,
//        image : MultipartBody.Part,
//        token: String): UpdateProfileResponse


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

    override suspend fun getProfile(token: String): GetProfileResponse {
        return apiService.getProfile(token)
    }

    override suspend fun updateProfile(token: String): UpdateProfileResponse {
        return apiService.putProfile(token)
    }




    override suspend fun postLogin(loginRequestBody: LoginRequestBody): LoginUserResponse {
        return apiService.postLogin(loginRequestBody)
    }


}