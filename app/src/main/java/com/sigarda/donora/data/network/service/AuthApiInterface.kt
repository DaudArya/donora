package com.sigarda.donora.data.network.service

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
import com.sigarda.donora.data.network.models.profile.profile.GetprofileRequestBody
import com.sigarda.donora.data.network.models.profile.updateProfile.Profile
import com.sigarda.donora.data.network.models.profile.updateProfile.UpdateProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part

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

    @GET("user-profile/my-profile")
    suspend fun getProfile(
        @Header("Authorization") token: String
    ): GetProfileResponse

    @Multipart
    @POST("user-profile/update")
    fun updateProfile(
        @Part("username") username: RequestBody,
        @Part("fullName") fullName: RequestBody,
        @Part("blood_id") blood_id : RequestBody,
        @Part("village_id") village_id: RequestBody,
        @Part("nik") nik: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("phone_number") phone_number : RequestBody,
        @Part("donor_code") donor_code: RequestBody,
        @Part("age") age : RequestBody,
        @Part("address") address: RequestBody,
        @Part image: MultipartBody.Part,
        @Header("Authorization") token: String): Call<UpdateProfileResponse>

    @POST("user-profile/update")
    suspend fun putProfile(token: String): UpdateProfileResponse



}