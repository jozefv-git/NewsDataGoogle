package com.jozefv.newsdata.di

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.jozefv.newsdata.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    // Define shared prefs module in app module because they are used across whole app
    single<SharedPreferences> {
        EncryptedSharedPreferences(
            androidApplication(),
            "encrypted_auth_prefs",
            MasterKey(
                androidApplication()
            ),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
    viewModelOf(::MainViewModel)
}