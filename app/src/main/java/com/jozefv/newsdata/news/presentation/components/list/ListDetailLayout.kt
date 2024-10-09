@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package com.jozefv.newsdata.news.presentation.components.list

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jozefv.newsdata.news.domain.ResultUi
import com.jozefv.newsdata.news.presentation.NewsAction
import com.jozefv.newsdata.news.presentation.NewsState

@Composable
fun ListDetailLayout(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    state: NewsState,
    onAction: (NewsAction) -> Unit
) {
    val navigator = rememberListDetailPaneScaffoldNavigator<ResultUi>()
    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }
    ListDetailPaneScaffold(
        modifier = modifier,
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            // Our List screen
            AnimatedPane {
                NewsList(
                    navigator = navigator,
                    paddingValues = paddingValues,
                    state = state,
                    onAction = {
                        onAction(it)
                    }
                )
            }
        },
        detailPane = {
            // Content from our clicked item - detail screen
            navigator.currentDestination?.content?.let { resultUi ->
                // Our Detail Screen with animation
                AnimatedPane {
                    NewsDetail(
                        paddingValues = paddingValues,
                        resultUi = resultUi,
                        onShareClick = {})
                }
            }
        }
    )
}