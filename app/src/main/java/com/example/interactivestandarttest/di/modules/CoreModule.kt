package com.example.interactivestandarttest.di.modules

import android.app.Application
import android.content.Context
import com.example.interactivestandarttest.presentation.utils.FileSaver
import com.example.interactivestandarttest.presentation.utils.FileSaverImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CoreModule(private val application: Application) {
    @Provides
    @Singleton
    fun provideContext(): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideFileSaver(context: Context): FileSaver {
        return FileSaverImpl(context)
    }
}