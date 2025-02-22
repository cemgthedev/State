package com.inovassessoria.state

import android.app.Application
import com.inovassessoria.state.di.appModule
import com.inovassessoria.state.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class StateApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@StateApplication)
            modules(listOf(appModule, networkModule))
        }
    }
}