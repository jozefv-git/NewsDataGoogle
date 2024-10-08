package com.jozefv.newsdata

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jozefv.newsdata.core.domain.SessionStorage
import kotlinx.coroutines.launch

class MainViewModel(private val sessionStorage: SessionStorage) : ViewModel() {
    var state by mutableStateOf(MainState())
        private set

    init {
        viewModelScope.launch {
            state = state.copy(isAuthenticating = true)
            state = state.copy(isLoggedIn = sessionStorage.getUser() != null)
            state = state.copy(isAuthenticating = false)
        }
    }
}