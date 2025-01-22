package com.compose.seekhoassignment.di

import android.app.Application
import android.util.Log
import org.koin.core.context.startKoin


class StartKoin: Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("StartKoin", "onCreate: Koin Method is executing")
        startKoin {
            modules(appModules)
        }
    }
}