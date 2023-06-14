package com.sigarda.donora.ui.activity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sigarda.donora.data.network.models.notification.NotificationModel
import com.sigarda.donora.data.network.service.FCMInterface
import com.sigarda.donora.data.notification.RetrofitClient
import com.sigarda.jurnalkas.di.NetworkModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject


//@Inject constructor(

//)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiService: FCMInterface
) : ViewModel() {

    private val _data: MutableLiveData<ResponseBody> = MutableLiveData()
    val data: LiveData<ResponseBody> = _data

    private val _connectionError = MutableStateFlow("")
    private val _response = MutableStateFlow("")

    val connectionError: LiveData<String>
        get() = _connectionError.asLiveData()

    val response: LiveData<String>
        get() = _response.asLiveData()

    fun sendNotification(notificationModel: NotificationModel) {


        viewModelScope.launch(Dispatchers.IO) {
            _connectionError.emit("sending")
            try {
                val response = RetrofitClient.apiService.sendNotification(notificationModel)
                if (response.isSuccessful) {
                    //_response.emit(response.toString())
                    Log.d("TAG", "Notification in Kotlin: ${response.body()} ")

                    _connectionError.emit("sent")
                } else {
                    _connectionError.emit("error while sending")
                    Log.d("TAG", "Notification in Kotlin1: ${response.errorBody()} ")

                }
            }catch (e:Exception){
                _connectionError.emit("error while sending")
                Log.d("TAG", "Notification in Kotlin2: ${e.message} ")

            }
        }

    }

}