package com.jozefv.newsdata.auth.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jozefv.newsdata.auth.domain.isUserValid
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel() : ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    private var notEmptyFields = snapshotFlow {
        state.email.isNotBlank() && state.password.isNotBlank()
    }

    private val _eventChannel = Channel<LoginEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        notEmptyFields.onEach {
            state = state.copy(
                notEmptyFields = it
            )
        }.launchIn(viewModelScope)
    }

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.EmailInput -> {
                state = state.copy(email = action.email)
            }

            is LoginAction.PasswordInput -> {
                state = state.copy(password = action.password)
            }

            LoginAction.OnPasswordVisibilityClick -> {
                state = state.copy(
                    isPasswordVisible = !state.isPasswordVisible
                )
            }

            LoginAction.OnLoginClick -> {
                state = state.copy(
                    canLogin = state.notEmptyFields && isUserValid(state.email, state.password)
                )

                if (state.canLogin!!) {
                    login()
                }
            }
            // Navigation is handled in NavigationRoot
            else -> Unit
        }
    }

    private fun login() {
        viewModelScope.launch {
            _eventChannel.send(LoginEvent.LoginSuccess)
        }
    }
}