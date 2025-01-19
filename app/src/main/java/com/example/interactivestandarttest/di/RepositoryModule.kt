package com.example.interactivestandarttest.di

import com.example.interactivestandarttest.data.network.ApiService
import com.example.interactivestandarttest.data.repository.FileRepositoryImpl
import com.example.interactivestandarttest.data.repository.PointRepositoryImpl
import com.example.interactivestandarttest.domain.repository.FileRepository
import com.example.interactivestandarttest.domain.repository.PointRepository
import com.example.interactivestandarttest.presentation.utils.FileSaver
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun providePointRepository(apiService: ApiService): PointRepository {
        return PointRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideFileRepository(fileSaver: FileSaver): FileRepository {
        return FileRepositoryImpl(fileSaver)
    }
}