package com.jozefv.newsdata.news.presentation

import com.jozefv.newsdata.core.presentation.UiText
import com.jozefv.newsdata.news.presentation.mappers.NewsDataUiParcelize

data class NewsState(
    val isLoggedIn: Boolean = false,
    val news: NewsDataUiParcelize = NewsDataUiParcelize(),
    val clickedListItemLocation: Int = 0,
    val isRefreshingNews: Boolean = false,
    val refreshedTime: String = "",
    val error: UiText? = null
)