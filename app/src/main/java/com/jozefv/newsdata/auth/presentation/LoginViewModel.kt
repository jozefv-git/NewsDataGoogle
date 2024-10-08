package com.jozefv.newsdata.auth.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.EmailInput -> {
                state = state.copy(
                    email = action.email
                )
            }

            is LoginAction.PasswordInput -> {
                state = state.copy(
                    password = action.password
                )
            }
            LoginAction.OnPasswordVisibilityClick -> {
                state = state.copy(
                    isPasswordVisible = !state.isPasswordVisible
                )
            }
            // Navigation is handled in NavigationRoot
            else -> Unit
        }
    }
}