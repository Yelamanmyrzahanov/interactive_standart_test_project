package com.example.interactivestandarttest.di.component

import android.app.Application
import com.example.interactivestandarttest.MainActivity
import com.example.interactivestandarttest.di.modules.CoreModule
import com.example.interactivestandarttest.di.modules.NetworkModule
import com.example.interactivestandarttest.di.modules.RepositoryModule
import com.example.interactivestandarttest.di.modules.UseCaseModule
import com.example.interactivestandarttest.presentation.chart.ChartFragment
import com.example.interactivestandarttest.presentation.main.MainFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        UseCaseModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        CoreModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun coreModule(coreModule: CoreModule): Builder
        fun build(): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: MainFragment)
    fun inject(fragment: ChartFragment)
}