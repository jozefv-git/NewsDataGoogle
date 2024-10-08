package com.jozefv.newsdata.core.di

import com.jozefv.newsdata.core.data.EncryptedSharedPrefSessionStorage
import com.jozefv.newsdata.core.domain.SessionStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreModule = module {
    singleOf(::EncryptedSharedPrefSessionStorage).bind<SessionStorage>()
}