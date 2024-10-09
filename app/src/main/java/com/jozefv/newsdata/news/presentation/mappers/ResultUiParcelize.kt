package com.jozefv.newsdata.news.presentation.mappers

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResultUiParcelize(
    val title: String,
    val imageUrl: String?,
    val description: String,
    val publishedDate: String,
    val sourceName: String,
    val sourceUrl: String,
    val articleLink: String
) : Parcelable