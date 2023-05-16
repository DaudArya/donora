package com.sigarda.jurnalkas.di

import com.sigarda.donora.data.repository.AuthApiRepository
import com.sigarda.donora.data.repository.AuthApiRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideAuthRepository(authRepositoryImpl: AuthApiRepositoryImpl): AuthApiRepository
}