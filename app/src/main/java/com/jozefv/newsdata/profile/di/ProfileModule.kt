package com.jozefv.newsdata.profile.di

import com.jozefv.newsdata.profile.presentation.ProfileScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val profileModule = module {
    viewModelOf(::ProfileScreenViewModel)
}