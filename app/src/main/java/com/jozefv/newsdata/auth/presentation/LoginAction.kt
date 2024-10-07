package com.jozefv.newsdata.auth.presentation

sealed interface LoginAction {
    data class EmailInput(val email: String): LoginAction
    data class PasswordInput(val password: String): LoginAction
    data object OnLoginClick : LoginAction
    data object OnSkipClick : LoginAction
    data object OnPasswordVisibilityClick : LoginAction
}