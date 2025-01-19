package com.example.interactivestandarttest

import android.app.Application
import com.example.interactivestandarttest.di.AppComponent
import com.example.interactivestandarttest.di.CoreModule
import com.example.interactivestandarttest.di.DaggerAppComponent

class MyApplication: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .coreModule(CoreModule(this))
            .build()
    }
}