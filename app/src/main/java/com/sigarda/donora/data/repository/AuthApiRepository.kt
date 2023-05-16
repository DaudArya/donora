package com.sigarda.donora.data.repository

import com.sigarda.donora.data.local.UserLocalDataSource
import com.sigarda.donora.data.network.datasource.AuthRemoteDataSource
import com.sigarda.donora.data.network.models.auth.login.requestbody.LoginRequestBody
import com.sigarda.donora.data.network.models.auth.login.response.LoginResponse
import com.sigarda.donora.data.network.models.auth.register.requestbody.RegisterRequestBody
import com.sigarda.donora.data.network.models.auth.register.response.RegisterResponse
import com.sigarda.donora.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface AuthApiRepository {

    suspend fun postRegisterUser(registerRequestBody: RegisterRequestBody): Resource<RegisterResponse>
    suspend fun postLoginUser(loginRequestBody: LoginRequestBody): Resource<LoginResponse>
    suspend fun setUserLogin(isLogin: Boolean)
    suspend fun setUserToken(isToken: String)
    suspend fun saveUserToken(isToken: String)
    fun getUserLoginStatus(): Flow<Boolean>
}

class AuthApiRepositoryImpl @Inject constructor(private val userLocalDataSource: UserLocalDataSource, private val dataSource: AuthRemoteDataSource) : AuthApiRepository {

    override suspend fun postRegisterUser(registerRequestBody: RegisterRequestBody): Resource<RegisterResponse> {
        return proceed {
            dataSource.postRegister(registerRequestBody)
        }
    }

    override suspend fun postLoginUser(loginRequestBody: LoginRequestBody): Resource<LoginResponse> {
        return proceed {
            dataSource.postLogin(loginRequestBody)
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