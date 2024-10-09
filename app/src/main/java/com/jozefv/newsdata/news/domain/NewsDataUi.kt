package com.jozefv.newsdata.news.domain

// Adjusted container used for displaying in the UI
data class NewsDataUi(
    val results: List<ResultUi>? = emptyList()
)
