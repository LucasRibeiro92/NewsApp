package com.example.newsapp

import android.app.Application
import com.example.newsapp.model.di.articleModule
import org.koin.android.ext.koin.androidContext

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        org.koin.core.context.startKoin {
            /** Context **/
            androidContext(this@MyApp)
            /** Room Database **/
            modules(articleModule)
        }
    }
}