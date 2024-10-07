package com.jozefv.newsdata.news.di

import com.jozefv.newsdata.news.presentation.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val newsModule = module {
    viewModelOf(::NewsViewModel)
}