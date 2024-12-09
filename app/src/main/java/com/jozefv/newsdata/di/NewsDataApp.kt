package com.jozefv.newsdata.di

import android.app.Application
import com.jozefv.newsdata.auth.di.authModule
import com.jozefv.newsdata.core.di.coreModule
import com.jozefv.newsdata.news.di.newsModule
import com.jozefv.newsdata.profile.di.profileModule
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
                appModule,
                authModule,
                newsModule,
                coreModule,
                profileModule
            )
        }
    }
}