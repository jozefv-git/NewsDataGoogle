package com.jozefv.newsdata.core.presentation

import com.jozefv.newsdata.R
import com.jozefv.newsdata.core.domain.ResultHandler
import com.jozefv.newsdata.news.domain.NewsDataUi

fun ResultHandler.Error<NewsDataUi,Int>.toUiText(): UiText {
    return when (this.errorCode) {
        401 -> UiText.StringResource(R.string.unauthorised)
        429 -> UiText.StringResource(R.string.too_many_requests)
        500 -> UiText.StringResource(R.string.internal_error)
        404 -> UiText.StringResource(R.string.no_internet)
        else -> UiText.StringResource(R.string.other_error)
    }
}