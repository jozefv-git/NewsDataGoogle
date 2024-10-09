package com.jozefv.newsdata.news.data.dto

import kotlinx.serialization.Serializable

/**
 * Result doesn't include all fields. Mostly because of API availability paid limitation.
 *
 * All fields can be found here: https://newsdata.io/documentation/#http_response
 *
 */

@Serializable
data class Result(
    val article_id: String,
    val category: ArrayList<String>,
    val country: ArrayList<String>?,
    val creator: ArrayList<String>?,
    val description: String?,
    val image_url: String?,
    val keywords: ArrayList<String>?,
    val language: String?,
    val link: String?,
    val pubDate: String?,
    val pubDateTZ: String?,
    val source_icon: String?,
    val source_name: String?,
    val source_url: String?,
    val title: String?,
    val video_url: String?
)