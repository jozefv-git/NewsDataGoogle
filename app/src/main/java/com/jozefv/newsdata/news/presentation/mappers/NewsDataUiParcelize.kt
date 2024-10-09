package com.jozefv.newsdata.news.presentation.mappers

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsDataUiParcelize(
    val results: List<ResultUiParcelize>? = emptyList()

) : Parcelable
