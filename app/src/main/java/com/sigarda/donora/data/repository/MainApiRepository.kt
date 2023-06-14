package com.sigarda.donora.data.repository

import com.sigarda.donora.data.network.datasource.MainRemoteDataSource
import com.sigarda.donora.data.network.models.dashboard.banner.BannerResponse
import com.sigarda.donora.data.network.models.dashboard.user.history.HistoryResponse
import com.sigarda.donora.data.network.models.dashboard.user.schedule.ScheduleResponse
import com.sigarda.donora.data.network.models.dashboard.user.title.TitleResponse
import com.sigarda.donora.data.network.models.leaderboard.bestplace.BestLeaderBoardResponse
import com.sigarda.donora.data.network.models.leaderboard.commonplace.AllLeaderBoardResponse
import com.sigarda.donora.data.network.models.schedule.ScheduleDonorResponse
import com.sigarda.donora.utils.Resource
import retrofit2.Call
import javax.inject.Inject

interface MainApiRepository {

    suspend fun getTitle(token : String): Resource<TitleResponse>
    suspend fun getHistory(token : String): Resource<HistoryResponse>
    suspend fun getSchedule(token : String): Resource<ScheduleResponse>
    suspend fun getBanner(): Resource<Call<BannerResponse>>
    suspend fun getScheduleDonor(): Resource<Call<ScheduleDonorResponse>>
    suspend fun getAllLeaderboard(): Resource<Call<AllLeaderBoardResponse>>
    suspend fun getBestLeaderboard(): Resource<Call<BestLeaderBoardResponse>>

}

class MainApiRepositoryImpl @Inject constructor(
    private val dataSource: MainRemoteDataSource
 ) :
    MainApiRepository {
    override suspend fun getTitle(token: String): Resource<TitleResponse> {
        return proceed {
            dataSource.getTitle(token)
        }
    }

    override suspend fun getHistory(token: String): Resource<HistoryResponse> {
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

    private suspend fun <T> proceed(coroutines: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutines.invoke())
        } catch (e: Exception) {
            Resource.Error(e, e.message)
        }
    }

}