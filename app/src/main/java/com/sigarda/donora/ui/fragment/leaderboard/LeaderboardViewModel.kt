package com.sigarda.donora.ui.fragment.leaderboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sigarda.donora.data.network.models.leaderboard.bestplace.BestLeaderBoardResponse
import com.sigarda.donora.data.network.models.leaderboard.commonplace.AllLeaderBoardResponse
import com.sigarda.donora.data.network.service.MainApiInterface
import com.sigarda.donora.data.repository.MainApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LeaderboardViewModel@Inject constructor(
    private val mainRepository: MainApiRepository,
    private val apiService : MainApiInterface

    ) : ViewModel() {

    private val _bestLeaderboard = MutableLiveData<List<com.sigarda.donora.data.network.models.leaderboard.bestplace.Data>>()
    val getBestLeaderBoardResponse: LiveData<List<com.sigarda.donora.data.network.models.leaderboard.bestplace.Data>> = _bestLeaderboard


    private val _leaderboard = MutableLiveData<List<com.sigarda.donora.data.network.models.leaderboard.commonplace.Data>>()
    val getAllLeaderBoardResponse: LiveData<List<com.sigarda.donora.data.network.models.leaderboard.commonplace.Data>> = _leaderboard


    fun getAllLeaderboard(){
        apiService.getAllLeaderboard().enqueue(object : Callback<AllLeaderBoardResponse> {
            override fun onResponse(
                call: Call<AllLeaderBoardResponse>,
                response: Response<AllLeaderBoardResponse>
            ) {
                if (response.isSuccessful) {
                    _leaderboard.postValue(response.body()!!.data as List<com.sigarda.donora.data.network.models.leaderboard.commonplace.Data>?)
                }
            }
            override fun onFailure(call: Call<AllLeaderBoardResponse>, t: Throwable) {
                Log.e("Error : ", "onFailure: ${t.message}")
            }
        })
    }

    fun getBestLeaderboard(){
        apiService.getBestLeaderboard().enqueue(object : Callback<BestLeaderBoardResponse> {
            override fun onResponse(
                call: Call<BestLeaderBoardResponse>,
                response: Response<BestLeaderBoardResponse>
            ) {
                if (response.isSuccessful) {
                    _bestLeaderboard.postValue(response.body()!!.data as List<com.sigarda.donora.data.network.models.leaderboard.bestplace.Data>?)
                }
            }
            override fun onFailure(call: Call<BestLeaderBoardResponse>, t: Throwable) {
                Log.e("Error : ", "onFailure: ${t.message}")
            }
        })
    }

    }