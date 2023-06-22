package com.sigarda.donora.ui.fragment.stock.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sigarda.donora.data.network.models.stock.BloodRequestBody
import com.sigarda.donora.data.network.models.stock.ProductData
import com.sigarda.donora.data.network.models.stock.StockBloodTypeResponse
import com.sigarda.donora.data.network.models.stock.vendor.Data
import com.sigarda.donora.data.network.models.stock.VendorBloodRequestBody
import com.sigarda.donora.data.network.models.stock.vendor.VendorBloodResponse
import com.sigarda.donora.data.network.models.stock.vendorBlood.StockBlood
import com.sigarda.donora.data.network.models.stock.vendorBlood.VendorResponse
import com.sigarda.donora.data.network.service.MainApiInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    private val _myBlood: MutableLiveData<Call<StockBloodTypeResponse>> = MutableLiveData()
    val getDataBlood: LiveData<Call<StockBloodTypeResponse>> = _myBlood

    private val _bloodData = MutableLiveData<List<ProductData>>()
    val getBloodResponse: LiveData<List<ProductData>> = _bloodData

    private val _bloodDataVendor = MutableLiveData<List<StockBlood>>()
    val getBloodVendorResponse: LiveData<List<StockBlood>> = _bloodDataVendor


    fun getBloodData(bloodRequestBody: BloodRequestBody) {
        viewModelScope.launch(Dispatchers.IO) {
            val ProfileGet = apiService.getBloodData(bloodRequestBody)
            viewModelScope.launch(Dispatchers.Main) {
                _myBlood.postValue(ProfileGet)
            }
        }
    }


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

    fun getVendorBlood(bloodRequestBody: VendorBloodRequestBody){
        apiService.getBloodDatabyVendor(bloodRequestBody).enqueue(object : Callback<VendorResponse> {
            override fun onResponse(
                call: Call<VendorResponse>,
                response: Response<VendorResponse>
            ) {
                if (response.isSuccessful) {
                    _bloodDataVendor.postValue(response.body()!!.data.stock_blood as List<StockBlood>?)
                }
            }
            override fun onFailure(call: Call<VendorResponse>, t: Throwable) {
                Log.e("Error : ", "onFailure: ${t.message}")
            }
        })
    }

}