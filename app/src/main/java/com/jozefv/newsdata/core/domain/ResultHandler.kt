package com.jozefv.newsdata.core.domain

interface ResultHandler<D,E> {
    data class Success<D,E>(val data: D): ResultHandler<D,E>
    data class Error<D,E>(val errorCode: E): ResultHandler<D,E>
}