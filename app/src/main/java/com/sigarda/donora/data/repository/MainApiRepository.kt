package com.sigarda.donora.data.repository

import com.sigarda.donora.data.local.UserLocalDataSource
import com.sigarda.donora.data.network.datasource.MainRemoteDataSource
import com.sigarda.donora.data.network.models.dashboard.banner.BannerResponse
import com.sigarda.donora.data.network.models.dashboard.user.history.HistoryResponse
import com.sigarda.donora.data.network.models.dashboard.user.schedule.ScheduleResponse
import com.sigarda.donora.data.network.models.dashboard.user.title.TitleResponse
import com.sigarda.donora.utils.Resource
import retrofit2.Call
import javax.inject.Inject

interface MainApiRepository {

    suspend fun getTitle(token : String): Resource<TitleResponse>
    suspend fun getHistory(token : String): Resource<HistoryResponse>
    suspend fun getSchedule(token : String): Resource<ScheduleResponse>
    suspend fun getBanner(): Resource<Call<BannerResponse>>

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

    private suspend fun <T> proceed(coroutines: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutines.invoke())
        } catch (e: Exception) {
            Resource.Error(e, e.message)
        }
    }

}