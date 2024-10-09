package com.jozefv.newsdata.news.presentation

import com.jozefv.newsdata.core.presentation.UiText

interface NewsEvent {
    data class LogOutEvent(val value: UiText): NewsEvent
    data class ErrorEvent(val value: UiText): NewsEvent
}