package com.sigarda.donora.data.repository

import com.sigarda.donora.data.local.UserLocalDataSource
import com.sigarda.donora.data.network.datasource.AuthRemoteDataSource
import com.sigarda.donora.data.network.models.auth.google.login.GoogleAuthRequestBody
import com.sigarda.donora.data.network.models.auth.google.login.LoginGoogleResponse
import com.sigarda.donora.data.network.models.auth.login.requestbody.LoginRequestBody
import com.sigarda.donora.data.network.models.auth.register.requestbody.RegisterRequestBody
import com.sigarda.donora.data.network.models.auth.register.response.RegisterResponse
import com.sigarda.donora.data.network.models.auth.create.CreateProfileRequestBody
import com.sigarda.donora.data.network.models.auth.create.CreateProfileResponse
import com.sigarda.donora.data.network.models.auth.login.response.LoginUserResponse
import com.sigarda.donora.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface AuthApiRepository {
    suspend fun postRegisterUser(registerRequestBody: RegisterRequestBody): Resource<RegisterResponse>
    suspend fun postLoginUser(loginRequestBody: LoginRequestBody): Resource<LoginUserResponse>
    suspend fun postLoginUserGoogle(googleAuthRequestBody: GoogleAuthRequestBody): Resource<LoginGoogleResponse>
    suspend fun createUser(token : String , createUserRequestBody: CreateProfileRequestBody): Resource<CreateProfileResponse>
    suspend fun setUserLogin(isLogin: Boolean)
    suspend fun setUserToken(isToken: String)
    suspend fun saveUserToken(isToken: String)
    suspend fun setUserName(isUsername: String)
    suspend fun getUserName(isUsername: String)
    suspend fun setFullName(isFullname: String)
    suspend fun getFullName(isFullname: String)
    suspend fun setPhoneNumber(isPhoneNumber: String)
    suspend fun getPhoneNumber(isPhoneNumber: String)
    suspend fun setNik(isNik: String)
    suspend fun getNik(isNik: String)
    suspend fun setGender(isGender: String)
    suspend fun getGender(isGender: String)
    suspend fun setDonorId(isDonorId: String)
    suspend fun getDonorId(isDonorId: String)
    fun getUserLoginStatus(): Flow<Boolean>
}

class AuthApiRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
    private val dataSource: AuthRemoteDataSource) :
    AuthApiRepository {

    override suspend fun postRegisterUser(registerRequestBody: RegisterRequestBody): Resource<RegisterResponse> {
        return proceed {
            dataSource.postRegister(registerRequestBody)
        }
    }

    override suspend fun postLoginUser(loginRequestBody: LoginRequestBody): Resource<LoginUserResponse> {
        return proceed {
            dataSource.postLogin(loginRequestBody)
        }
    }

    override suspend fun postLoginUserGoogle(googleAuthRequestBody: GoogleAuthRequestBody): Resource<LoginGoogleResponse> {
        return proceed {
            dataSource.postLoginGoogle(googleAuthRequestBody)
        }
    }

    override suspend fun createUser(
        token: String,
        createUserRequestBody: CreateProfileRequestBody
    ): Resource<CreateProfileResponse> {
        return proceed {
            dataSource.postCreateUser(token,createUserRequestBody)
        }
    }


    override suspend fun setUserLogin(isLogin: Boolean) {
        return userLocalDataSource.setUserLogin(isLogin)
    }

    override suspend fun setUserToken(isToken: String) {
        return userLocalDataSource.setUserToken(isToken)
    }

    override suspend fun saveUserToken(isToken: String) {
        return userLocalDataSource.SaveUserToken(isToken)
    }

    override suspend fun setUserName(isUsername: String) {
        return userLocalDataSource.setUserName(isUsername)
    }

    override suspend fun getUserName(isUsername: String) {
        return userLocalDataSource.getUserName(isUsername)
    }

    override suspend fun setFullName(isFullname: String) {
        return userLocalDataSource.setFullName(isFullname)
    }

    override suspend fun getFullName(isFullname: String) {
        return userLocalDataSource.getFullName(isFullname)
    }

    override suspend fun setPhoneNumber(isPhoneNumber: String) {
        return userLocalDataSource.setPhoneNumber(isPhoneNumber)
    }

    override suspend fun getPhoneNumber(isPhoneNumber: String) {
        return userLocalDataSource.setPhoneNumber(isPhoneNumber)
    }

    override suspend fun setNik(isNik: String) {
        return userLocalDataSource.setNik(isNik)
    }

    override suspend fun getNik(isNik: String) {
        return userLocalDataSource.setNik(isNik)
    }

    override suspend fun setGender(isGender: String) {
        return userLocalDataSource.setGender(isGender)
    }

    override suspend fun getGender(isGender: String) {
        return userLocalDataSource.getGender(isGender)
    }

    override suspend fun setDonorId(isDonorId: String) {
        return userLocalDataSource.setDonorId(isDonorId)
    }

    override suspend fun getDonorId(isDonorId: String) {
        return userLocalDataSource.getDonorId(isDonorId)
    }

    override fun getUserLoginStatus(): Flow<Boolean> {
        return userLocalDataSource.getUserLoginStatus()
    }


    private suspend fun <T> proceed(coroutines: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutines.invoke())
        } catch (e: Exception) {
            Resource.Error(e, e.message)
        }
    }

}