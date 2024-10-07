package com.jozefv.newsdata.auth.presentation

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoggingIn: Boolean = false
)
