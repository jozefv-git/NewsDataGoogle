@file:OptIn(ExperimentalMaterial3Api::class)

package com.jozefv.newsdata.news.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jozefv.newsdata.core.presentation.SpacerVerL
import com.jozefv.newsdata.core.presentation.components.CustomScaffold
import com.jozefv.newsdata.core.presentation.components.CustomToolBar
import com.jozefv.newsdata.news.presentation.components.list.ListDetailLayout
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsScreenRoot(
    onLoginClick: () -> Unit,
    viewModel: NewsViewModel = koinViewModel()
) {
    NewsScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                is NewsAction.OnLoginClick -> onLoginClick()
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
private fun NewsScreen(
    state: NewsState,
    onAction: (NewsAction) -> Unit
) {
    CustomScaffold(
        modifier = Modifier.fillMaxSize(),
        topAppBar = {
            CustomToolBar(
                title = "Latest News",
                trailingContent = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            Icons.Default.AccountCircle,
                            contentDescription = Icons.Default.AccountCircle.name
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        // TODO: !state.isLoggedIn
        if (state.isLoggedIn) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "To be able to check latest news, please login.")
                SpacerVerL()
                Button(onClick = {
                    onAction(NewsAction.OnLoginClick)
                }) {
                    Text(text = "Login")
                }
            }
        } else {
            ListDetailLayout(
                modifier = Modifier.fillMaxSize(),
                paddingValues = paddingValues,
                state = state,
                onAction = {
                    onAction(it)
                }
            )
        }
    }

}

@Preview
@Composable
private fun NewsScreenPreview() {
// Add theme here
    MaterialTheme {
        NewsScreen(
            state = NewsState(),
            onAction = {}
        )
    }
}
