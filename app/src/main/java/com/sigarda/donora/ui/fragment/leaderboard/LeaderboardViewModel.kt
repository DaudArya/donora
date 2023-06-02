package com.sigarda.donora.ui.fragment.leaderboard

import androidx.lifecycle.ViewModel
import com.sigarda.donora.data.local.UserDataStoreManager
import com.sigarda.donora.data.repository.AuthApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LeaderboardViewModel@Inject constructor(
    private val dataStoreManager: UserDataStoreManager,
    private val authRepository: AuthApiRepository,

    ) : ViewModel() {

    }