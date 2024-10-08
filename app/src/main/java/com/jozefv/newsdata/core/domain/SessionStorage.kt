package com.jozefv.newsdata.core.domain

interface SessionStorage {
    suspend fun getUser(): AuthUser?
    suspend fun setUser(user: AuthUser?)
}