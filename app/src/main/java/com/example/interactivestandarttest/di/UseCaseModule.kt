package com.example.interactivestandarttest.di

import com.example.interactivestandarttest.domain.repository.FileRepository
import com.example.interactivestandarttest.domain.repository.PointRepository
import com.example.interactivestandarttest.domain.usecase.GetPointsUseCase
import com.example.interactivestandarttest.domain.usecase.SaveChartToFileUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Provides
    @Singleton
    fun provideGetPointsUseCase(pointRepository: PointRepository): GetPointsUseCase {
        return GetPointsUseCase(pointRepository)
    }

    @Provides
    @Singleton
    fun provideSaveChartToFileUseCase(fileRepository: FileRepository): SaveChartToFileUseCase {
        return SaveChartToFileUseCase(fileRepository)
    }
}