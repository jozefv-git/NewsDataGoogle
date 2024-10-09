package com.jozefv.newsdata.news.data.dto

import kotlinx.serialization.Serializable

// DAO for communication with https://newsdata.io/documentation/#about-newdata-api
@Serializable
data class NewsDataDao(
    val results: ArrayList<Result>?,
    val status: String,
    val totalResults: Int,
    val nextPage: String?
)