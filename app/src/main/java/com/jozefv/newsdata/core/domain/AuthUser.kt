package com.jozefv.newsdata.core.domain

data class AuthUser(
    val email: String?,
    val password: String?,
    val idToken: String?,
    val displayName: String?,
    val profilePictureUrl: String?,
)
