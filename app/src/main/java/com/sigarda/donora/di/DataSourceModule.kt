package com.sigarda.donora.di

import com.sigarda.donora.data.local.UserLocalDataSource
import com.sigarda.donora.data.local.UserLocalDataSourceImpl
import com.sigarda.donora.data.network.datasource.AuthRemoteDataSource
import com.sigarda.donora.data.network.datasource.AuthRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun provideAuthRemoteDataSource(authRemoteDataSourceImpl: AuthRemoteDataSourceImpl): AuthRemoteDataSource

    @Binds
    abstract fun provideUserLocalDataSource(userLocalDataSourceImpl: UserLocalDataSourceImpl): UserLocalDataSource
}