package com.sigarda.donora.data.network.datasource

import com.sigarda.donora.data.network.models.dashboard.banner.BannerResponse
import com.sigarda.donora.data.network.models.dashboard.user.history.HistoryResponse
import com.sigarda.donora.data.network.models.dashboard.user.schedule.ScheduleResponse
import com.sigarda.donora.data.network.models.dashboard.user.title.TitleResponse
import com.sigarda.donora.data.network.models.profile.getProfile.GetProfileResponse
import com.sigarda.donora.data.network.service.MainApiInterface
import retrofit2.Call
import javax.inject.Inject

interface MainRemoteDataSource {

    suspend fun getBannner(): Call<BannerResponse>

    suspend fun getHistory(token : String): HistoryResponse

    suspend fun getTitle(token : String): TitleResponse

    suspend fun getSchedule(token : String): ScheduleResponse
}

class MainRemoteDataSourceImpl @Inject constructor(private val apiService: MainApiInterface) :
    MainRemoteDataSource {
    override suspend fun getBannner(): Call<BannerResponse> {
        return apiService.getBanner()
    }

    override suspend fun getHistory(token: String): HistoryResponse {
        return apiService.getHistory(token)
    }

    override suspend fun getTitle(token: String): TitleResponse {
        return apiService.getlevel(token)
    }

    override suspend fun getSchedule(token: String): ScheduleResponse {
        return apiService.getSchedule(token)
    }
}