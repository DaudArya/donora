package com.sigarda.donora.ui.fragment.editprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sigarda.donora.data.local.UserDataStoreManager
import com.sigarda.donora.data.network.models.profile.updateProfile.UpdateProfileResponse
import com.sigarda.donora.data.network.service.AuthApiInterface
import com.sigarda.donora.data.repository.AuthApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val authRepository: AuthApiRepository,
    private val apiClient :AuthApiInterface

    ) : ViewModel() {

    private val _update: MutableLiveData<UpdateProfileResponse?> = MutableLiveData()
    val update: LiveData<UpdateProfileResponse?> get() = _update

    fun updateUser(
        username : RequestBody,
        fullName : RequestBody,
        blood_id : RequestBody ,
        village_id : RequestBody,
        nik : RequestBody,
        gender : RequestBody,
        phone_number : RequestBody,
        donor_code : RequestBody,
        age : RequestBody,
        address : RequestBody,
        image : MultipartBody.Part,
        token: String
    ){
        apiClient.updateProfile(username, fullName, blood_id, village_id, nik, gender, phone_number, donor_code, age, address, image, token)
            .enqueue(object : Callback<UpdateProfileResponse> {
                override fun onResponse(
                    call: Call<UpdateProfileResponse>,
                    response: Response<UpdateProfileResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _update.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<UpdateProfileResponse>, t: Throwable) {
                    _update.postValue(null)
                }
            })
    }

    }