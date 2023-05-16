package com.sigarda.donora.ui.fragment.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sigarda.donora.data.local.UserDataStoreManager
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
    private val dataStoreManager: UserDataStoreManager,
    private val authRepository: AuthApiRepository,
) : ViewModel() {
    private var _postRegisterUserResponse = MutableLiveData<Resource<RegisterResponse>>()
    val postRegisterUserResponse: LiveData<Resource<RegisterResponse>> get() = _postRegisterUserResponse

    fun postRegisterUser(registerRequestBody: RegisterRequestBody) {
        viewModelScope.launch(Dispatchers.IO) {
            val registerResponse = authRepository.postRegisterUser(registerRequestBody)
            viewModelScope.launch(Dispatchers.Main) {
                _postRegisterUserResponse.postValue(registerResponse)
            }
        }
    }

}