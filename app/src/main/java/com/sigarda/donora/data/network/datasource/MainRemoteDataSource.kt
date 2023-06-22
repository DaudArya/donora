package com.sigarda.donora.data.network.datasource

import com.sigarda.donora.data.network.models.dashboard.banner.BannerResponse
import com.sigarda.donora.data.network.models.dashboard.user.history.PastDonorResponse
import com.sigarda.donora.data.network.models.dashboard.user.schedule.ScheduleResponse
import com.sigarda.donora.data.network.models.dashboard.user.title.TitleResponse
import com.sigarda.donora.data.network.models.history.Create.CreateHistoryRequestBody
import com.sigarda.donora.data.network.models.history.Create.CreateHistoryResponse
import com.sigarda.donora.data.network.models.history.Get.GetHistoryResponse
import com.sigarda.donora.data.network.models.leaderboard.bestplace.BestLeaderBoardResponse
import com.sigarda.donora.data.network.models.leaderboard.commonplace.AllLeaderBoardResponse
import com.sigarda.donora.data.network.models.schedule.ScheduleDonorResponse

import com.sigarda.donora.data.network.models.stock.StockBloodTypeResponse
import com.sigarda.donora.data.network.service.MainApiInterface
import com.sigarda.donora.utils.Resource
import retrofit2.Call
import javax.inject.Inject

interface MainRemoteDataSource {
    suspend fun getBannner(): Call<BannerResponse>
    suspend fun getBloodPerType(): Call<StockBloodTypeResponse>
    suspend fun getScheduleDonor(): Call<ScheduleDonorResponse>
    suspend fun getAllLeaderboard(): Call<AllLeaderBoardResponse>
    suspend fun getBestLeaderboard(): Call<BestLeaderBoardResponse>
    suspend fun getHistory(token : String): PastDonorResponse
    suspend fun getTitle(token : String): TitleResponse

    suspend fun getMyHistory(token : String): GetHistoryResponse
    suspend fun getSchedule(token : String): ScheduleResponse
    suspend fun postHistory(requestBody: CreateHistoryRequestBody ): Resource<CreateHistoryResponse>
}

class MainRemoteDataSourceImpl @Inject constructor(
    private val apiService: MainApiInterface) :
    MainRemoteDataSource {
    override suspend fun getBannner(): Call<BannerResponse> {
        return apiService.getBanner()
    }

    override suspend fun getBloodPerType(): Call<StockBloodTypeResponse> {
        return apiService.getBloodPerType()
    }

    override suspend fun getScheduleDonor(): Call<ScheduleDonorResponse> {
        return apiService.getScheduleDonor()
    }

    override suspend fun getAllLeaderboard(): Call<AllLeaderBoardResponse> {
        return apiService.getAllLeaderboard()
    }

    override suspend fun getBestLeaderboard(): Call<BestLeaderBoardResponse> {
        return apiService.getBestLeaderboard()
    }

    override suspend fun getHistory(token: String): PastDonorResponse {
        return apiService.getHistory(token)
    }

    override suspend fun getTitle(token: String): TitleResponse {
        return apiService.getlevel(token)
    }

    override suspend fun getMyHistory(token: String): GetHistoryResponse {
        return apiService.getMyHistory(token)
    }

    override suspend fun getSchedule(token: String): ScheduleResponse {
        return apiService.getSchedule(token)
    }

    override suspend fun postHistory(requestBody: CreateHistoryRequestBody): Resource<CreateHistoryResponse> {
        return apiService.postCreateHistory(requestBody)
    }
}