package com.jozefv.newsdata.news.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jozefv.newsdata.R
import com.jozefv.newsdata.core.domain.ResultHandler
import com.jozefv.newsdata.core.domain.SessionStorage
import com.jozefv.newsdata.core.presentation.UiText
import com.jozefv.newsdata.core.presentation.toUiText
import com.jozefv.newsdata.news.domain.NewsRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class NewsViewModel(
    private val newsRepository: NewsRepository,
    private val sessionStorage: SessionStorage
) : ViewModel() {

    var state by mutableStateOf(NewsState())
        private set

    private val _channel = Channel<NewsEvent>()
    val channel = _channel.receiveAsFlow()

    init {
        viewModelScope.launch {
            state = state.copy(isLoggedIn = sessionStorage.getUser() != null)
            state = state.copy(isRefreshingNews = true)
            when (val result = newsRepository.getNews()) {
                is ResultHandler.Success -> {
                    state = state.copy(
                        news = result.data,
                        refreshedTime = getLocalTime()
                    )
                    state = state.copy(isRefreshingNews = false)
                }

                is ResultHandler.Error -> {
                    state = state.copy(
                        error = result.toUiText()
                    )
                    state = state.copy(isRefreshingNews = false)
                }
            }
        }
    }

    fun onAction(action: NewsAction) {
        when (action) {
            NewsAction.OnLogoutClick -> {
                viewModelScope.launch {
                    sessionStorage.setUser(null)
                    _channel.send(NewsEvent.LogOutEvent(UiText.StringResource(R.string.logout)))
                }
            }

            // If we are at the bottom of the list - load more data
            NewsAction.OnLoadMoreNews -> {
                viewModelScope.launch {
                    when (val result = newsRepository.getMoreNews()) {
                        is ResultHandler.Success -> {
                            state = state.copy(
                                news = result.data
                            )
                            state = state.copy(
                                error = null
                            )
                        }

                        is ResultHandler.Error -> {
                            _channel.send(NewsEvent.ErrorEvent(result.toUiText()))
                        }
                    }
                }
            }

            // Pull down or refresh click
            NewsAction.OnRefresh -> {
                viewModelScope.launch {
                    state = state.copy(isRefreshingNews = true)
                    when (val result = newsRepository.getNews(refreshNews = true)) {
                        is ResultHandler.Success -> {
                            state = state.copy(
                                news = result.data,
                                refreshedTime = getLocalTime()
                            )
                            state = state.copy(
                                isRefreshingNews = false,
                                // There is no error anymore- reset state
                                error = null
                            )
                        }

                        is ResultHandler.Error -> {
                            _channel.send(NewsEvent.ErrorEvent(result.toUiText()))
                            state = state.copy(isRefreshingNews = false)
                        }
                    }
                }
            }

            is NewsAction.LocationOfTheItem -> {
                state = state.copy(clickedListItemLocation = action.position)
            }
            // Navigation is handled in NavigationRoot
            else -> Unit
        }
    }
}

private fun getLocalTime(): String {
    val currentDateTime = LocalDateTime.parse(LocalDateTime.now().toString())
    val zone = ZoneId.systemDefault()

    return currentDateTime.atZone(zone)
        .format(DateTimeFormatter.ofPattern("HH:mm:ss"))
}