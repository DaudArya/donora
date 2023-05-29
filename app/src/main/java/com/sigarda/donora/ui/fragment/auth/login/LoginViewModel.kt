package com.sigarda.donora.ui.fragment.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sigarda.donora.data.local.UserDataStoreManager
import com.sigarda.donora.data.network.models.auth.create.CreateProfileRequestBody
import com.sigarda.donora.data.network.models.auth.create.CreateProfileResponse
import com.sigarda.donora.data.network.models.auth.google.login.GoogleAuthRequestBody
import com.sigarda.donora.data.network.models.auth.google.login.LoginGoogleResponse
import com.sigarda.donora.data.network.models.auth.login.requestbody.LoginRequestBody
import com.sigarda.donora.data.network.models.auth.login.response.LoginUserResponse
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

    private var _postLoginUserResponse = MutableLiveData<Resource<LoginUserResponse>>()
    val postLoginUserResponse: LiveData<Resource<LoginUserResponse>> get() = _postLoginUserResponse

    private var _postLoginGoogleResponse = MutableLiveData<Resource<LoginGoogleResponse>>()
    val postLoginGoogleResponse: LiveData<Resource<LoginGoogleResponse>> get() = _postLoginGoogleResponse

    private var _postCreateUserResponse = MutableLiveData<Resource<CreateProfileResponse>>()
    val postCreateUserResponse: LiveData<Resource<CreateProfileResponse>> get() = _postCreateUserResponse


    fun login(loginRequestBody: LoginRequestBody) {
        viewModelScope.launch(Dispatchers.IO) {
            val loginResponse = authRepository.postLoginUser(loginRequestBody)
            viewModelScope.launch(Dispatchers.Main) {
                _postLoginUserResponse.postValue(loginResponse)
            }
        }
    }

    fun postCreateUser(token: String, createUserRequestBody: CreateProfileRequestBody) {
        viewModelScope.launch(Dispatchers.IO) {
            _postCreateUserResponse.postValue(Resource.Loading())
            val response = authRepository.createUser(token, createUserRequestBody)
            viewModelScope.launch(Dispatchers.Main) {
                _postCreateUserResponse.postValue(response)
            }
        }
    }

    fun loginGoogle(googleAuthRequestBody: GoogleAuthRequestBody) {
        viewModelScope.launch(Dispatchers.IO) {
            val loginGoogleResponse = authRepository.postLoginUserGoogle(googleAuthRequestBody)
            viewModelScope.launch(Dispatchers.Main) {
                _postLoginGoogleResponse.postValue(loginGoogleResponse)
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