package com.jozefv.newsdata.news.domain

import com.jozefv.newsdata.core.domain.ResultHandler

interface NewsRepository {
    suspend fun getNews(refreshNews: Boolean = false): ResultHandler<NewsDataUi,Int>
    suspend fun getMoreNews(): ResultHandler<NewsDataUi,Int>
}