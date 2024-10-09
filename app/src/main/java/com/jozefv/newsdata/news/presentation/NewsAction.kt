package com.jozefv.newsdata.news.presentation

sealed interface NewsAction {
    data object OnLoginClick: NewsAction
    data object OnLogoutClick: NewsAction
    data object OnLoadMoreNews: NewsAction
    data object OnRefresh: NewsAction
    data class LocationOfTheItem(val position: Int): NewsAction
}