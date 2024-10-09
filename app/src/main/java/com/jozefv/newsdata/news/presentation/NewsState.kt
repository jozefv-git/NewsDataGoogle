package com.jozefv.newsdata.news.presentation

import com.jozefv.newsdata.news.domain.NewsDataUi

data class NewsState(
    val isLoggedIn: Boolean = false,
    val news: NewsDataUi = NewsDataUi(),
    val clickedListItemLocation: Int = 0,
    val isRefreshingNews: Boolean = false,
    val refreshedTime: String = ""
)