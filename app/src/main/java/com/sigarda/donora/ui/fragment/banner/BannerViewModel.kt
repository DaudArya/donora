package com.sigarda.donora.ui.fragment.banner

import androidx.lifecycle.ViewModel
import com.sigarda.donora.data.local.UserDataStoreManager
import com.sigarda.donora.data.network.service.MainApiInterface
import com.sigarda.donora.data.repository.MainApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BannerViewModel @Inject constructor(
    val dataStoreManager: UserDataStoreManager,
    val mainApiRepository: MainApiRepository,
    val apiService : MainApiInterface
)
    : ViewModel() {

    }