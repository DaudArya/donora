package com.sigarda.donora.data.network.service

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
import com.sigarda.donora.data.network.models.stock.BloodRequestBody
import com.sigarda.donora.data.network.models.stock.StockBloodTypeResponse
import com.sigarda.donora.data.network.models.stock.VendorBloodRequestBody
import com.sigarda.donora.data.network.models.stock.vendorBlood.VendorResponse
import com.sigarda.donora.utils.Resource
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part

interface MainApiInterface {

    @GET("request-blood/accepted")
    fun getBanner() : Call<BannerResponse>

    @GET("stock-blood/products")
    fun getBloodPerType() : Call<StockBloodTypeResponse>

    @GET("donorSchedule")
    fun getScheduleDonor() : Call<ScheduleDonorResponse>

    @POST("stock-blood/products")
    fun getListBloodData(@Body requestBody: BloodRequestBody): Call<StockBloodTypeResponse>

    @POST("stock-blood/products")
    fun getBloodData(@Body requestBody: BloodRequestBody): Call<StockBloodTypeResponse>

    @POST("stock-blood/products/vendor")
    fun getBloodDatabyVendor(@Body requestBody: VendorBloodRequestBody): Call<VendorResponse>

    @POST("history/create")
    fun postCreateHistory(@Body requestBody: CreateHistoryRequestBody): Resource<CreateHistoryResponse>

    @GET("user-profile/first-podium")
    fun getBestLeaderboard() : Call<BestLeaderBoardResponse>
    @GET("user-profile/next-podium")
    fun getAllLeaderboard() : Call<AllLeaderBoardResponse>

    @GET("user-profile/my-level")
    suspend fun getlevel(
        @Header("Authorization") token: String
    ): TitleResponse

    @GET("history/my")
    suspend fun getHistory(
        @Header("Authorization") token: String
    ): PastDonorResponse

    @GET("history/my-detail")
    suspend fun getMyHistory(
        @Header("Authorization") token: String
    ): GetHistoryResponse

    @GET("history/my-detail")
    fun getListHistory(@Header ("Authorization") token: String): Call<GetHistoryResponse>

    @GET("history/nextDonor")
    suspend fun getSchedule(
        @Header("Authorization") token: String
    ): ScheduleResponse


    @Multipart
    @POST("history/create")
    fun postHistoryUser(
        @Part("description") description : RequestBody,
        @Part("date") date : RequestBody,
        @Part receipt_donor : MultipartBody.Part,
        @Header("Authorization") token: String): Call <CreateHistoryResponse>

}