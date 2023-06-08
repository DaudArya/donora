package com.sigarda.donora.ui.fragment.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sigarda.donora.data.local.UserDataStoreManager
import com.sigarda.donora.data.network.models.auth.login.response.LoginUserResponse
import com.sigarda.donora.data.network.models.dashboard.banner.BannerResponse
import com.sigarda.donora.data.network.models.dashboard.banner.Data
import com.sigarda.donora.data.network.models.dashboard.user.history.HistoryResponse
import com.sigarda.donora.data.network.models.dashboard.user.schedule.ScheduleResponse
import com.sigarda.donora.data.network.models.dashboard.user.title.TitleResponse
import com.sigarda.donora.data.network.models.profile.getProfile.GetProfileResponse
import com.sigarda.donora.data.network.service.AuthApiInterface
import com.sigarda.donora.data.network.service.MainApiInterface
import com.sigarda.donora.data.repository.AuthApiRepository
import com.sigarda.donora.data.repository.MainApiRepository
import com.sigarda.donora.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val dataStoreManager: UserDataStoreManager,
    val mainApiRepository: MainApiRepository,
    val apiService : MainApiInterface
    )
    : ViewModel() {

    private var _postLoginUserResponse = MutableLiveData<Resource<LoginUserResponse>>()
    val postLoginUserResponse: LiveData<Resource<LoginUserResponse>> get() = _postLoginUserResponse

    private var _historyResponse = MutableLiveData<Resource<HistoryResponse>>()
    val getHistoryResponse: LiveData<Resource<HistoryResponse>> get() = _historyResponse

    private var _scheduleResponse = MutableLiveData<Resource<ScheduleResponse>>()
    val getScheduleResponse: LiveData<Resource<ScheduleResponse>> get() = _scheduleResponse

    private var _titleResponse = MutableLiveData<Resource<TitleResponse>>()
    val getTitleResponse: LiveData<Resource<TitleResponse>> get() = _titleResponse

    private val _banner = MutableLiveData<List<Data>>()
    val getBannerResponse: LiveData<List<Data>> = _banner

    fun getTitle(token : String) {
        viewModelScope.launch(Dispatchers.IO) {
            val titleGet = mainApiRepository.getTitle(token)
            viewModelScope.launch(Dispatchers.Main) {
                _titleResponse.postValue(titleGet)
            }
        }
    }

    fun getSchedule(token : String) {
        viewModelScope.launch(Dispatchers.IO) {
            val scheduleGet = mainApiRepository.getSchedule(token)
            viewModelScope.launch(Dispatchers.Main) {
                _scheduleResponse.postValue(scheduleGet)
            }
        }
    }

    fun getHistory(token : String) {
        viewModelScope.launch(Dispatchers.IO) {
            val historyGet = mainApiRepository.getHistory(token)
            viewModelScope.launch(Dispatchers.Main) {
                _historyResponse.postValue(historyGet)
            }
        }
    }

    fun getTicket(){
        apiService.getBanner().enqueue(object : Callback<BannerResponse> {
            override fun onResponse(
                call: Call<BannerResponse>,
                response: Response<BannerResponse>
            ) {
                if (response.isSuccessful) {
                    _banner.postValue(response.body()!!.data as List<Data>?)
                }
            }
            override fun onFailure(call: Call<BannerResponse>, t: Throwable) {
                Log.e("Error : ", "onFailure: ${t.message}")
            }
        })
    }
}


