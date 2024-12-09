package com.jozefv.newsdata.auth.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.credentials.CustomCredential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.jozefv.newsdata.auth.domain.isUserValid
import com.jozefv.newsdata.core.domain.AuthUser
import com.jozefv.newsdata.core.domain.SessionStorage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val sessionStorage: SessionStorage,
) : ViewModel() {
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
                viewModelScope.launch {
                    state = state.copy(
                        canLogin = state.notEmptyFields && isUserValid(state.email, state.password)
                    )

                    if (state.canLogin!!) {
                        login(
                            AuthUser(
                                email = state.email,
                                password = state.password,
                                null,
                                null,
                                null
                            )
                        )
                    }
                }
            }

            is LoginAction.OnLoginWithGoogleClick -> {
                viewModelScope.launch {
                    val credential = action.credentials
                    if (credential is CustomCredential && credential.type
                        == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
                    ) {
                        val userCredentials =
                            GoogleIdTokenCredential.createFrom(credential.data)
                        login(
                            AuthUser(
                                email = null,
                                password = null,
                                idToken = userCredentials.idToken,
                                displayName = userCredentials.displayName,
                                profilePictureUrl = userCredentials.profilePictureUri.toString()
                            )
                        )
                    } else {
                        Log.e("Credential error", "Unexpected credential")
                    }
                }
            }
            // Navigation is handled in NavigationRoot
            else -> Unit
        }
    }

    private suspend fun login(authUser: AuthUser) {
        sessionStorage.setUser(authUser)
        _eventChannel.send(LoginEvent.LoginSuccess)
    }
}