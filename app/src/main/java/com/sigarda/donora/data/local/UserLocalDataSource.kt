package com.sigarda.donora.data.local

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UserLocalDataSource {
    suspend fun setUserLogin(isLogin: Boolean)
    suspend fun setUserToken(isToken: String)
    suspend fun SaveUserToken(isToken: String)
    fun getUserLoginStatus(): Flow<Boolean>

//    suspend fun setNotif(isLogin: Boolean)
//    fun getNotif(): Flow<Boolean>

}

class UserLocalDataSourceImpl @Inject constructor(
    private val userDataStore: UserDataStoreManager
) : UserLocalDataSource {

    override suspend fun setUserLogin(isLogin: Boolean) {
        userDataStore.setUserLogin(isLogin)
    }

    override suspend fun setUserToken(isToken: String) {
        userDataStore.setToken(isToken)
    }

    override suspend fun SaveUserToken(isToken: String) {
        userDataStore.GetToken(isToken)
    }

    override fun getUserLoginStatus(): Flow<Boolean> {
        return userDataStore.getUserLoginStatus()
    }

//    override fun getNotif(): Flow<Boolean> {
//        return userDataStore.getNotifStatus()
//    }
//
//    override suspend fun setNotif(isNotif: Boolean) {
//        userDataStore.setNotif(isNotif)
//    }
}