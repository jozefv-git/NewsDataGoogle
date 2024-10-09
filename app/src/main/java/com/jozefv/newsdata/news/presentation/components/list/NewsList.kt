@file:OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)

package com.jozefv.newsdata.news.presentation.components.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jozefv.newsdata.core.presentation.SpacerHorXS
import com.jozefv.newsdata.core.presentation.SpacerVerM
import com.jozefv.newsdata.news.domain.ResultUi
import com.jozefv.newsdata.news.presentation.NewsAction
import com.jozefv.newsdata.news.presentation.NewsState

@Composable
fun NewsList(
    modifier: Modifier = Modifier,
    navigator: ThreePaneScaffoldNavigator<ResultUi>,
    paddingValues: PaddingValues,
    state: NewsState,
    onAction: (NewsAction) -> Unit
) {
    // When coming back from detail screen - show at the top of the list our clicked item
    val listState =
        rememberLazyListState(initialFirstVisibleItemIndex = state.clickedListItemLocation)

    val pullToRefreshState = rememberPullToRefreshState()
    state.news.results?.let { news ->
        PullToRefreshBox(
            modifier = modifier.padding(paddingValues),
            state = pullToRefreshState,
            isRefreshing = state.isRefreshingNews,
            onRefresh = { onAction(NewsAction.OnRefresh) },
            content = {
                Column(modifier = modifier.padding(horizontal = 16.dp, vertical = 24.dp)) {
                    Row(
                        modifier
                            .fillMaxWidth()
                            .clickable {
                                onAction(NewsAction.OnRefresh)
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Updated at: ${state.refreshedTime}"
                        )
                        SpacerHorXS()
                        if (!state.isRefreshingNews) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = Icons.Default.Refresh,
                                contentDescription = Icons.Default.Refresh.name
                            )
                        }
                    }
                    SpacerVerM()
                    LazyColumn(
                        state = listState,
                        modifier = modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        itemsIndexed(news) { index, resultUi ->
                            NewsCard(
                                resultUi = resultUi,
                                onClick = {
                                    onAction(NewsAction.LocationOfTheItem(index))
                                    // Navigate to detail screen with provided content
                                    navigator.navigateTo(
                                        pane = ListDetailPaneScaffoldRole.Detail,
                                        content = resultUi
                                    )
                                }
                            )
                            // Bottom of the list
                            if (resultUi == news.last()) {
                                onAction(NewsAction.OnLoadMoreNews)
                            }
                        }
                    }
                }
            }
        )
    }
}