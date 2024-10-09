package com.jozefv.newsdata.news.di

import com.jozefv.newsdata.news.data.HttpClientFactory
import com.jozefv.newsdata.news.data.NewsRepositoryKtorImpl
import com.jozefv.newsdata.news.domain.NewsRepository
import com.jozefv.newsdata.news.presentation.NewsViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val newsModule = module {
    single {
        HttpClientFactory().build(CIO.create())
    }

    singleOf(::NewsRepositoryKtorImpl).bind<NewsRepository>()
    viewModelOf(::NewsViewModel)
}