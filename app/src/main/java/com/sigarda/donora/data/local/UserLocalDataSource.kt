package com.sigarda.donora.data.local

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UserLocalDataSource {
    suspend fun setUserLogin(isLogin: Boolean)
    suspend fun setUserToken(isToken: String)
    suspend fun SaveUserToken(isToken: String)
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

class UserLocalDataSourceImpl @Inject constructor(
    private val userDataStore: UserDataStoreManager
) : UserLocalDataSource {

    override suspend fun setUserLogin(isLogin: Boolean) {
        userDataStore.setUserLogin(isLogin)
    }

    override suspend fun SaveUserToken(isToken: String) {
        userDataStore.GetToken(isToken)
    }

    override suspend fun setUserName(isUsername: String) {
        userDataStore.setUsername(isUsername)
    }

    override suspend fun getUserName(isUsername: String) {
        userDataStore.getUsername(isUsername)
    }

    override suspend fun setFullName(isFullname: String) {
        userDataStore.setFullName(isFullname)
    }

    override suspend fun getFullName(isFullname: String) {
        userDataStore.getFullname(isFullname)
    }

    override suspend fun setPhoneNumber(isPhoneNumber: String) {
        userDataStore.setPhoneNumber(isPhoneNumber)
    }

    override suspend fun getPhoneNumber(isPhoneNumber: String) {
        userDataStore.getPhoneNumber(isPhoneNumber)
    }

    override suspend fun setNik(isNik: String) {
        userDataStore.setNik(isNik)
    }

    override suspend fun getNik(isNik: String) {
        userDataStore.getNik(isNik)
    }

    override suspend fun setGender(isGender: String) {
        userDataStore.setGender(isGender)
    }

    override suspend fun getGender(isGender: String) {
        userDataStore.getGender(isGender)
    }

    override suspend fun setDonorId(isDonorId: String) {
        userDataStore.setDonorId(isDonorId)
    }

    override suspend fun getDonorId(isDonorId: String) {
        userDataStore.getDonorId(isDonorId)
    }

    override suspend fun setUserToken(isToken: String) {
        userDataStore.setToken(isToken)
    }

    override fun getUserLoginStatus(): Flow<Boolean> {
        return userDataStore.getUserLoginStatus()
    }

}