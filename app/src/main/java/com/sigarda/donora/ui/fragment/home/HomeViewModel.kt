package com.sigarda.donora.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sigarda.donora.data.local.UserDataStoreManager
import com.sigarda.donora.data.network.models.auth.login.response.LoginResponse
import com.sigarda.donora.data.network.service.AuthApiInterface
import com.sigarda.donora.data.repository.AuthApiRepository
import com.sigarda.donora.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val dataStoreManager: UserDataStoreManager, private val userRepository: AuthApiRepository, private val ApiClient: AuthApiInterface ) : ViewModel() {


    private var _postLoginUserResponse = MutableLiveData<Resource<LoginResponse>>()
    val postLoginUserResponse: LiveData<Resource<LoginResponse>> get() = _postLoginUserResponse

    fun statusLogin(isLogin: Boolean) {
        viewModelScope.launch {
            dataStoreManager.statusLogin(isLogin)
        }
    }

    fun getLoginStatus(): LiveData<Boolean> {
        return dataStoreManager.getLoginStatus().asLiveData()
    }
}