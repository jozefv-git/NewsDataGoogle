package com.jozefv.newsdata.news.presentation

sealed interface NewsAction {
    data object OnLoginClick: NewsAction
    data object OnLogoutClick: NewsAction
}