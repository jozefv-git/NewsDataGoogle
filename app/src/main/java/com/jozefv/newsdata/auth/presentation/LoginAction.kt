package com.jozefv.newsdata.auth.presentation

import androidx.credentials.Credential

sealed interface LoginAction {
    data class EmailInput(val email: String): LoginAction
    data class PasswordInput(val password: String): LoginAction
    data object OnLoginClick : LoginAction
    data class OnLoginWithGoogleClick(val credentials: Credential): LoginAction
    data object OnSkipClick : LoginAction
    data object OnPasswordVisibilityClick : LoginAction
}