package com.compose.seekhoassignment.di

import android.app.Application
import com.compose.seekhoassignment.MainActivity
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

class StartKoin: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            module {
                appModules
            }
        }
    }
}