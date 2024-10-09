package com.jozefv.newsdata.news.domain

data class ResultUi(
    val title: String,
    val imageUrl: String?,
    val description: String,
    val publishedDate: String,
    val sourceName: String,
    val sourceUrl: String
)
