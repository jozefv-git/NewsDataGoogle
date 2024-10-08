package com.jozefv.newsdata.auth.presentation

sealed interface LoginEvent {
    data object LoginSuccess: LoginEvent
}