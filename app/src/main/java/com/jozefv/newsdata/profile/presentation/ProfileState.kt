package com.jozefv.newsdata.profile.presentation

data class ProfileState(
    val displayName: String? = null,
    val profilePictureUrl: String? = null,
    val isLoggedWithGoogle: Boolean = false
)
