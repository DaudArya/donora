package com.sigarda.donora.data.repository

import com.sigarda.donora.data.local.UserLocalDataSource
import com.sigarda.donora.data.network.datasource.MainRemoteDataSource
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
import com.sigarda.donora.utils.Resource
import retrofit2.Call
import javax.inject.Inject

interface MainApiRepository {

    suspend fun getTitle(token : String): Resource<TitleResponse>
    suspend fun getHistory(token : String): Resource<PastDonorResponse>
    suspend fun getSchedule(token : String): Resource<ScheduleResponse>
    suspend fun getBanner(): Resource<Call<BannerResponse>>
    suspend fun setFCMToken(isToken: String)
    suspend fun getFCMToken(isToken: String)
    suspend fun getScheduleDonor(): Resource<Call<ScheduleDonorResponse>>
    suspend fun getAllLeaderboard(): Resource<Call<AllLeaderBoardResponse>>
    suspend fun getBestLeaderboard(): Resource<Call<BestLeaderBoardResponse>>

    suspend fun getMyHistory(token : String): Resource<GetHistoryResponse>
    suspend fun postCreateHistory(requestBody: CreateHistoryRequestBody): Resource<CreateHistoryResponse>

}

class MainApiRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
    private val dataSource: MainRemoteDataSource
 ) :
    MainApiRepository {
    override suspend fun getTitle(token: String): Resource<TitleResponse> {
        return proceed {
            dataSource.getTitle(token)
        }
    }

    override suspend fun getHistory(token: String): Resource<PastDonorResponse> {
        return proceed {
            dataSource.getHistory(token)
        }
    }

    override suspend fun getSchedule(token: String): Resource<ScheduleResponse> {
        return proceed {
            dataSource.getSchedule(token)
        }
    }

    override suspend fun getBanner(): Resource<Call<BannerResponse>> {
        return proceed {
            dataSource.getBannner()
        }
    }
    override suspend fun setFCMToken(isToken: String) {
        return userLocalDataSource.setFCMToken(isToken)
    }

    override suspend fun getFCMToken(isToken: String) {
        return userLocalDataSource.getFCMToken(isToken)
    }

    override suspend fun getScheduleDonor(): Resource<Call<ScheduleDonorResponse>> {
        return proceed {
            dataSource.getScheduleDonor()
        }
    }

    override suspend fun getAllLeaderboard(): Resource<Call<AllLeaderBoardResponse>> {
        return proceed {
            dataSource.getAllLeaderboard()
        }
    }

    override suspend fun getBestLeaderboard(): Resource<Call<BestLeaderBoardResponse>> {
        return proceed {
            dataSource.getBestLeaderboard()
        }
    }

    override suspend fun getMyHistory(token: String): Resource<GetHistoryResponse> {
        return proceed {
            dataSource.getMyHistory(token)
        }
    }

    override suspend fun postCreateHistory(requestBody: CreateHistoryRequestBody): Resource<CreateHistoryResponse> {
            return dataSource.postHistory(requestBody)
    }

    private suspend fun <T> proceed(coroutines: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutines.invoke())
        } catch (e: Exception) {
            Resource.Error(e, e.message)
        }
    }

}