package com.sigarda.jurnalkas.di

import com.sigarda.donora.data.network.service.AuthApiInterface
import com.sigarda.donora.data.network.service.FCMInterface
import com.sigarda.donora.data.network.service.MainApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseUrl1

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseUrl2



    const val BASE_URL = "https://donora.projectdira.my.id/public/api/"



    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideOkHttp(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()}


    private  val logging : HttpLoggingInterceptor
        get(){
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            return httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }

    private val client = OkHttpClient.Builder().addInterceptor(logging).build()


    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()


    @Singleton
    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthApiInterface =
        retrofit.create(AuthApiInterface::class.java)

    @Singleton
    @Provides
    fun provideMainApi(retrofit: Retrofit): MainApiInterface =
        retrofit.create(MainApiInterface::class.java)

    @Singleton
    @Provides
    fun provideFCMApi(retrofit: Retrofit): FCMInterface =
        retrofit.create(FCMInterface::class.java)

}