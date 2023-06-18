package com.sigarda.donora.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDataStoreManager @Inject constructor(@ApplicationContext private val context: Context) {

    suspend fun setUserLogin(isLogin: Boolean) {
        context.userDataStore.edit { preferences ->
            preferences[LOGIN_STATUS_KEY] = isLogin
        }
    }

    fun getUserLoginStatus(): Flow<Boolean> {
        return context.userDataStore.data.map { preferences ->
            preferences[LOGIN_STATUS_KEY] ?: false
        }
    }


    suspend fun statusLogin(isLogin: Boolean) {
        context.userDataStore.edit {
            it[LOGIN_STATUS] = isLogin
        }
    }

    fun getEmail(): Flow<String> {
        return context.userDataStore.data.map {
            it[EMAIL] ?: ""
        }
    }

    suspend fun getUsername(isUsername: String) {
        context.userDataStore.edit {
            it[USERNAME] = isUsername
        }
    }

    suspend fun getFullname(isFullname: String) {
        context.userDataStore.edit {
            it[FULLNAME] = isFullname
        }
    }
    suspend fun getPhoneNumber(isPhoneNumber: String) {
        context.userDataStore.edit {
            it[PHONE_NUMBER] = isPhoneNumber
        }
    }

    suspend fun getNik(isNik: String) {
        context.userDataStore.edit {
            it[NIK] = isNik
        }
    }



    suspend fun getGender(isGender: String) {
        context.userDataStore.edit {
            it[GENDER] = isGender
        }
    }

    suspend fun getDonorId(isDonorId: String) {
        context.userDataStore.edit {
            it[DONOR_ID] = isDonorId
        }
    }

    suspend fun GetToken(token: String) {
        context.userDataStore.edit {
            it[TOKEN] = token
        }
    }

    suspend fun GetTokenFCM(token: String) {
        context.userDataStore.edit {
            it[TOKEN_FCM] = token
        }
    }


    suspend fun setTokenFCM(isToken: String) {
        context.userDataStore.edit { preferences ->
            preferences[TOKEN_FCM] = isToken
        }
    }


    suspend fun setToken(isToken: String) {
        context.userDataStore.edit { preferences ->
            preferences[TOKEN] = isToken
        }
    }

    suspend fun setUsername(isUsername: String) {
        context.userDataStore.edit { preferences ->
            preferences[USERNAME] = isUsername
        }
    }
    suspend fun setFullName(isFullname: String) {
        context.userDataStore.edit { preferences ->
            preferences[FULLNAME] = isFullname
        }
    }

    suspend fun setPhoneNumber(isPhone: String) {
        context.userDataStore.edit { preferences ->
            preferences[PHONE_NUMBER] = isPhone
        }
    }
    suspend fun setNik(isNik: String) {
        context.userDataStore.edit { preferences ->
            preferences[NIK] = isNik
        }
    }

    suspend fun setGender(isGender: String) {
        context.userDataStore.edit { preferences ->
            preferences[GENDER] = isGender
        }
    }
    suspend fun setDonorId(isDonorId: String) {
        context.userDataStore.edit { preferences ->
            preferences[DONOR_ID] = isDonorId
        }
    }


    val getToken: Flow<String> = context.userDataStore.data.map {
        it[TOKEN] ?: ""
    }



    fun getLoginStatus(): Flow<Boolean> {
        return context.userDataStore.data.map {
            it[LOGIN_STATUS] ?: false
        }
    }



    companion object {
        private const val DATA_STORE_NAME = "user_preferences"

        private val USERNAME = stringPreferencesKey("username_key")
        private val TOKEN = stringPreferencesKey("token")
        private val TOKEN_FCM = stringPreferencesKey("token_fcm")
        private val ID_USER = stringPreferencesKey("id_user")
        private val PASSWORD = stringPreferencesKey("password_key")
        private val EMAIL = stringPreferencesKey("email_key")
        private val ADDRESS = stringPreferencesKey("address_key")
        private val FULLNAME = stringPreferencesKey("fullname_key")
        private val PHONE_NUMBER = stringPreferencesKey("phone_number_key")
        private val NIK = stringPreferencesKey("nik_key")
        private val GENDER = stringPreferencesKey("gender_key")
        private val DONOR_ID = stringPreferencesKey("donor_id_key")
        private val LOGIN_STATUS = booleanPreferencesKey("login_status_key")

        private val LOGIN_STATUS_KEY = booleanPreferencesKey("login_status_key")

        val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)
    }
}