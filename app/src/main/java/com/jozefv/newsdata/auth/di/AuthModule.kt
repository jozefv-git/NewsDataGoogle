package com.jozefv.newsdata.auth.di

import com.jozefv.newsdata.auth.presentation.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val authModule = module {
    viewModelOf(::LoginViewModel)
}