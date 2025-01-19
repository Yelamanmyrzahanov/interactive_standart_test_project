package com.example.interactivestandarttest.di

import android.app.Application
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.example.interactivestandarttest.data.network.ApiService
import com.example.interactivestandarttest.data.network.OkHttpProvider
import com.example.interactivestandarttest.data.network.RetrofitProvider
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideChuckerCollector(application: Application): ChuckerCollector {
        return ChuckerCollector(
            context = application,
            showNotification = true, // Show notification for collected data
            retentionPeriod = RetentionManager.Period.ONE_HOUR // Retain data for one hour
        )
    }

    @Singleton
    @Provides
    fun provideChuckerInterceptor(
        application: Application,
        chuckerCollector: ChuckerCollector
    ): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context = application)
            .collector(collector = chuckerCollector)
            .maxContentLength(length = 250_000L)
            .redactHeaders("Auth-Token", "Bearer") // Redact sensitive headers
            .alwaysReadResponseBody(enable = true)
            .createShortcut(enable = true) // Enable shortcut for Chucker UI
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(chuckerInterceptor: ChuckerInterceptor): OkHttpClient {
        return OkHttpProvider.provideOkHttpClient(chuckerInterceptor = chuckerInterceptor)
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return RetrofitProvider.provideRetrofit(okHttpClient)
    }
}