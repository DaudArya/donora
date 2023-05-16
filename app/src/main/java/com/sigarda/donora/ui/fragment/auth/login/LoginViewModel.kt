package com.sigarda.donora.ui.fragment.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sigarda.donora.data.local.UserDataStoreManager
import com.sigarda.donora.data.network.models.auth.login.requestbody.LoginRequestBody
import com.sigarda.donora.data.network.models.auth.login.response.LoginResponse
import com.sigarda.donora.data.repository.AuthApiRepository
import com.sigarda.donora.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val dataStoreManager: UserDataStoreManager,
    private val authRepository: AuthApiRepository,

) : ViewModel() {

    private var _postLoginUserResponse = MutableLiveData<Resource<LoginResponse>>()
    val postLoginUserResponse: LiveData<Resource<LoginResponse>> get() = _postLoginUserResponse


    fun login(loginRequestBody: LoginRequestBody) {
        viewModelScope.launch(Dispatchers.IO) {
            val loginResponse = authRepository.postLoginUser(loginRequestBody)
            viewModelScope.launch(Dispatchers.Main) {
                _postLoginUserResponse.postValue(loginResponse)
            }
        }
    }

    fun statusLogin(isLogin: Boolean) {
        viewModelScope.launch {
            dataStoreManager.statusLogin(isLogin)
        }
    }

    fun getLoginStatus(): LiveData<Boolean> {
        return dataStoreManager.getLoginStatus().asLiveData()
    }

    fun SaveUserToken(isToken: String) {
        viewModelScope.launch {
            authRepository.saveUserToken(isToken)
        }
    }

    fun getDataStoreToken(): LiveData<String> {
        return dataStoreManager.getToken.asLiveData()
    }

    fun getUserLoginStatus(): LiveData<Boolean> {
        return authRepository.getUserLoginStatus().asLiveData()
    }

    fun setUserLogin(isLogin: Boolean) {
        viewModelScope.launch {
            authRepository.setUserLogin(isLogin)
        }
    }

    fun setUserToken(isToken: String) {
        viewModelScope.launch {
            authRepository.setUserToken(isToken)
        }
    }

    fun postLoginUser(loginRequestBody: LoginRequestBody) {
        viewModelScope.launch(Dispatchers.IO) {
            val loginResponse = authRepository.postLoginUser(loginRequestBody)
            viewModelScope.launch(Dispatchers.Main) {
                _postLoginUserResponse.postValue(loginResponse)
            }
        }
    }
}