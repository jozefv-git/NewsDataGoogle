package com.jozefv.newsdata

import android.app.Application
import com.jozefv.newsdata.auth.di.authModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NewsDataApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Let koin know about our app context, so it can be used to create dependencies
            androidContext(this@NewsDataApp)
            // List of modules what we need
            modules(
                authModule
            )
        }
    }
}