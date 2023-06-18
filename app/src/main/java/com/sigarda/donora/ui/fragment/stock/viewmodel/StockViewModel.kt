package com.sigarda.donora.ui.fragment.stock.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sigarda.donora.data.network.models.leaderboard.commonplace.AllLeaderBoardResponse
import com.sigarda.donora.data.network.models.stock.BloodRequestBody
import com.sigarda.donora.data.network.models.stock.Data
import com.sigarda.donora.data.network.models.stock.ProductData
import com.sigarda.donora.data.network.models.stock.StockBloodTypeResponse
import com.sigarda.donora.data.network.service.MainApiInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class StockViewModel @Inject constructor(
    val apiService : MainApiInterface
    ) : ViewModel(){

    val sharedValue = MutableLiveData<Int>()

    private val _bloodData = MutableLiveData<List<ProductData>>()
    val getBloodResponse: LiveData<List<ProductData>> = _bloodData



    fun listBloodData(bloodRequestBody: BloodRequestBody){
        apiService.getListBloodData(bloodRequestBody).enqueue(object : Callback<StockBloodTypeResponse> {
            override fun onResponse(
                call: Call<StockBloodTypeResponse>,
                response: Response<StockBloodTypeResponse>
            ) {
                if (response.isSuccessful) {
                    _bloodData.postValue(response.body()!!.data.product_data as List<ProductData>?)
                }
            }
            override fun onFailure(call: Call<StockBloodTypeResponse>, t: Throwable) {
                Log.e("Error : ", "onFailure: ${t.message}")
            }
        })
    }

}