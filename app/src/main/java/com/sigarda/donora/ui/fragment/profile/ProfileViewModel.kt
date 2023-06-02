package com.sigarda.donora.ui.fragment.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sigarda.donora.data.local.UserDataStoreManager
import com.sigarda.donora.data.network.models.profile.getProfile.GetProfileResponse
import com.sigarda.donora.data.network.models.profile.profile.GetProfileUserResponse
import com.sigarda.donora.data.repository.AuthApiRepository
import com.sigarda.donora.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataStoreManager: UserDataStoreManager,
    private val authRepository: AuthApiRepository,

    ) : ViewModel() {

    private var _ProfileResponse = MutableLiveData<Resource<GetProfileResponse>>()
    val GetProfileUserResponse: LiveData<Resource<GetProfileResponse>> get() = _ProfileResponse

    private val _user: MutableLiveData<GetProfileResponse> = MutableLiveData()
    val user: LiveData<GetProfileResponse?> get() = _user

    fun GetProfileUser(token : String) {
        viewModelScope.launch(Dispatchers.IO) {
            val ProfileGet = authRepository.getProfile(token)
            viewModelScope.launch(Dispatchers.Main) {
                _ProfileResponse.postValue(ProfileGet)
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
    }