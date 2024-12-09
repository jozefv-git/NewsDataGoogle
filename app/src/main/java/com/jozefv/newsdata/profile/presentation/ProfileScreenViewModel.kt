package com.jozefv.newsdata.profile.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.credentials.CustomCredential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.jozefv.newsdata.core.domain.AuthUser
import com.jozefv.newsdata.core.domain.SessionStorage
import kotlinx.coroutines.launch

class ProfileScreenViewModel(private val sessionStorage: SessionStorage) : ViewModel() {
    var state by mutableStateOf(ProfileState())

    init {
        viewModelScope.launch {
            getGoogleLoggedUser()
        }
    }

    fun onAction(action: ProfileAction) {
        when (action) {
            is ProfileAction.OnLogin -> {
                viewModelScope.launch {
                    // First logout old user - so we can authenticate with Google
                    logout()
                    val credential = action.credential
                    if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                        val user = GoogleIdTokenCredential.createFrom(credential.data)
                        login(
                            AuthUser(
                                email = null,
                                password = null,
                                idToken = user.idToken,
                                displayName = user.displayName,
                                profilePictureUrl = user.profilePictureUri.toString()
                            )
                        )
                        getGoogleLoggedUser()
                    }
                }
            }

            ProfileAction.OnLogout -> {
                viewModelScope.launch {
                    logout()
                }
            }
        }
    }

    private suspend fun logout() {
        sessionStorage.setUser(null)
    }

    private suspend fun login(authUser: AuthUser) {
        sessionStorage.setUser(authUser)
    }

    private suspend fun getGoogleLoggedUser() {
        val loggedUser = sessionStorage.getUser()
        if (loggedUser != null) {
            state = state.copy(
                isLoggedWithGoogle = !loggedUser.idToken.isNullOrBlank(),
                displayName = loggedUser.displayName,
                profilePictureUrl = loggedUser.profilePictureUrl
            )
        }
    }
}