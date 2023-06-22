package com.sigarda.donora.ui.fragment.history

import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.sigarda.donora.R
import com.sigarda.donora.data.network.models.history.Create.CreateHistoryRequestBody
import com.sigarda.donora.data.network.models.history.Create.CreateHistoryResponse
import com.sigarda.donora.data.network.models.history.Get.GetHistoryResponse
import com.sigarda.donora.data.network.models.history.Get.TotalHistory
import com.sigarda.donora.data.network.service.MainApiInterface
import com.sigarda.donora.data.repository.MainApiRepository
import com.sigarda.donora.utils.Extension.showLongToast
import com.sigarda.donora.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.grpc.internal.JsonUtil.getString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(

    val apiService : MainApiInterface,
    val mainApiRepository: MainApiRepository
)
    : ViewModel() {

    private val _historyDataVendor = MutableLiveData<List<TotalHistory>>()
    val getHistoryVendorResponse: LiveData<List<TotalHistory>> = _historyDataVendor

    private var _historyUserResponse = MutableLiveData<Resource<GetHistoryResponse>>()
    val getHistoryUserResponse: LiveData<Resource<GetHistoryResponse>> get() = _historyUserResponse

    private var _postHistoryResponse = MutableLiveData<Resource<CreateHistoryResponse>>()
    val postHistoryResponse: LiveData<Resource<CreateHistoryResponse>> get() = _postHistoryResponse

    private val _postHistoryUser: MutableLiveData<CreateHistoryResponse?> = MutableLiveData()

    val postHistory: LiveData<CreateHistoryResponse?> get() = _postHistoryUser



    fun getHistoryUser(token : String) {
        viewModelScope.launch(Dispatchers.IO) {
            val HistoryUserGet = mainApiRepository.getMyHistory(token)
            viewModelScope.launch(Dispatchers.Main) {
                _historyUserResponse.postValue(HistoryUserGet)
            }
        }
    }

    fun listBloodData(token: String){
        apiService.getListHistory(token).enqueue(object :
            Callback<GetHistoryResponse> {
            override fun onResponse(
                call: Call<GetHistoryResponse>,
                response: Response<GetHistoryResponse>
            ) {
                if (response.isSuccessful) {
                    _historyDataVendor.postValue(response.body()!!.data.totalHistory as List<TotalHistory>?)
                }
            }
            override fun onFailure(call: Call<GetHistoryResponse>, t: Throwable) {
                Log.e("Error : ", "onFailure: ${t.message}")

            }
        })
    }


    fun postHistoryUser(
        description: RequestBody,
        date: RequestBody,
        receipt_donor: MultipartBody.Part,
        token: String
    ){
        apiService.postHistoryUser(description,date,receipt_donor,token)
            .enqueue(object : Callback<CreateHistoryResponse> {
                override fun onResponse(
                    call: Call<CreateHistoryResponse>,
                    response: Response<CreateHistoryResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _postHistoryUser.postValue(responseBody)
                        }
                    }
                }
                override fun onFailure(call: Call<CreateHistoryResponse>, t: Throwable) {
                    _postHistoryUser.postValue(null)
                    Log.e("Error : ", "onFailure: ${t.message}")
                }
            })
    }

    fun postCreateHistory(requestBody: CreateHistoryRequestBody) {
        viewModelScope.launch(Dispatchers.IO) {
            val craeteHistoryResponse = mainApiRepository.postCreateHistory(requestBody)
            viewModelScope.launch(Dispatchers.Main) {
                _postHistoryResponse.postValue(craeteHistoryResponse)
            }
        }
    }
}