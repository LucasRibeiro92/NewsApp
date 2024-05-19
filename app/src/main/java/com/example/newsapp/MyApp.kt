package com.example.newsapp

import android.app.Application
import com.example.newsapp.domain.di.MyAppModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(MyAppModules)

        }
    }
}