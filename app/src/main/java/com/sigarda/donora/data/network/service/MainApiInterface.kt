package com.sigarda.donora.data.network.service

import com.sigarda.donora.data.network.models.dashboard.banner.BannerResponse
import com.sigarda.donora.data.network.models.dashboard.user.history.HistoryResponse
import com.sigarda.donora.data.network.models.dashboard.user.schedule.ScheduleResponse
import com.sigarda.donora.data.network.models.dashboard.user.title.TitleResponse
import com.sigarda.donora.data.network.models.leaderboard.bestplace.BestLeaderBoardResponse
import com.sigarda.donora.data.network.models.leaderboard.commonplace.AllLeaderBoardResponse
import com.sigarda.donora.data.network.models.schedule.ScheduleDonorResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface MainApiInterface {

    @GET("requestBlood/accepted")
    fun getBanner() : Call<BannerResponse>

    @GET("donorSchedule")
    fun getScheduleDonor() : Call<ScheduleDonorResponse>

    @GET("user-profile/first-podium")
    fun getBestLeaderboard() : Call<AllLeaderBoardResponse>
    @GET("user-profile/next-podium")
    fun getAllLeaderboard() : Call<AllLeaderBoardResponse>

    @GET("user-profile/my-level")
    suspend fun getlevel(
        @Header("Authorization") token: String
    ): TitleResponse

    @GET("history/my")
    suspend fun getHistory(
        @Header("Authorization") token: String
    ): HistoryResponse

    @GET("history/nextDonor")
    suspend fun getSchedule(
        @Header("Authorization") token: String
    ): ScheduleResponse


}