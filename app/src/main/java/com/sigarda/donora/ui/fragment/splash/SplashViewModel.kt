package com.sigarda.donora.ui.fragment.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sigarda.donora.data.local.UserDataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(

    private val dataStoreManager: UserDataStoreManager,
): ViewModel() {

    val email = dataStoreManager.getEmail().asLiveData()

    fun getLoginStatus(): LiveData<Boolean> {
        return dataStoreManager.getLoginStatus().asLiveData()
    }
}