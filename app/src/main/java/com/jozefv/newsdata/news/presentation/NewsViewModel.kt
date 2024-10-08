package com.jozefv.newsdata.news.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class NewsViewModel: ViewModel() {

    var state by mutableStateOf(NewsState())
        private set

    fun onAction(action: NewsAction){
        when(action){
            NewsAction.OnLogoutClick -> {
                TODO()
            }
            // Navigation is handled in NavigationRoot
            else -> Unit
        }
    }
}