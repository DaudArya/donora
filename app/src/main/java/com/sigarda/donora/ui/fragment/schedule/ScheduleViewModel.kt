package com.sigarda.donora.ui.fragment.schedule

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sigarda.donora.data.network.models.schedule.Data
import com.sigarda.donora.data.network.models.schedule.ScheduleDonorResponse
import com.sigarda.donora.data.network.service.MainApiInterface
import com.sigarda.donora.data.repository.MainApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val apiService : MainApiInterface,
    private val mainRepository: MainApiRepository

    ) : ViewModel() {

    private val _schedule = MutableLiveData<List<Data>>()
    val getScheduleResponse: LiveData<List<Data>> = _schedule


    fun getDonorSchedule(){
        apiService.getScheduleDonor().enqueue(object : Callback<ScheduleDonorResponse> {
            override fun onResponse(
                call: Call<ScheduleDonorResponse>,
                response: Response<ScheduleDonorResponse>
            ) {
                if (response.isSuccessful) {
                    _schedule.postValue(response.body()!!.data as List<Data>?)
                }
            }
            override fun onFailure(call: Call<ScheduleDonorResponse>, t: Throwable) {
                Log.e("Error : ", "onFailure: ${t.message}")
            }
        })
    }

}