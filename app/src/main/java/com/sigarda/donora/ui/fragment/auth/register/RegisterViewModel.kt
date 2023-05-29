package com.sigarda.donora.ui.fragment.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sigarda.donora.data.local.UserDataStoreManager
import com.sigarda.donora.data.network.models.auth.create.CreateProfileRequestBody
import com.sigarda.donora.data.network.models.auth.create.CreateProfileResponse
import com.sigarda.donora.data.network.models.auth.google.login.GoogleAuthRequestBody
import com.sigarda.donora.data.network.models.auth.google.login.LoginGoogleResponse
import com.sigarda.donora.data.network.models.auth.register.requestbody.RegisterRequestBody
import com.sigarda.donora.data.network.models.auth.register.response.RegisterResponse
import com.sigarda.donora.data.repository.AuthApiRepository
import com.sigarda.donora.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthApiRepository
) : ViewModel() {
    private var _postRegisterUserResponse = MutableLiveData<Resource<RegisterResponse>>()
    val postRegisterUserResponse: LiveData<Resource<RegisterResponse>> get() = _postRegisterUserResponse

    private var _postLoginGoogleResponse = MutableLiveData<Resource<LoginGoogleResponse>>()
    val postLoginGoogleResponse: LiveData<Resource<LoginGoogleResponse>> get() = _postLoginGoogleResponse

    private var _postCreateUserResponse = MutableLiveData<Resource<CreateProfileResponse>>()
    val postCreateUserResponse: LiveData<Resource<CreateProfileResponse>> get() = _postCreateUserResponse

    fun postRegisterUser(registerRequestBody: RegisterRequestBody) {
        viewModelScope.launch(Dispatchers.IO) {
            val registerResponse = authRepository.postRegisterUser(registerRequestBody)
            viewModelScope.launch(Dispatchers.Main) {
                _postRegisterUserResponse.postValue(registerResponse)
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

    fun postCreateUser(token: String, createUserRequestBody: CreateProfileRequestBody) {
        viewModelScope.launch(Dispatchers.IO) {
            _postCreateUserResponse.postValue(Resource.Loading())
            val response = authRepository.createUser(token, createUserRequestBody)
            viewModelScope.launch(Dispatchers.Main) {
                _postCreateUserResponse.postValue(response)
            }
        }
    }

}