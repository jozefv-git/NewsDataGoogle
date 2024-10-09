@file:OptIn(ExperimentalMaterial3Api::class)

package com.jozefv.newsdata.news.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.jozefv.newsdata.R
import com.jozefv.newsdata.core.presentation.ObserveAsEvents
import com.jozefv.newsdata.core.presentation.SpacerVerL
import com.jozefv.newsdata.core.presentation.SpacerVerM
import com.jozefv.newsdata.core.presentation.components.CustomScaffold
import com.jozefv.newsdata.core.presentation.components.CustomToolBar
import com.jozefv.newsdata.news.presentation.components.list.ListDetailLayout
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsScreenRoot(
    onLoginClick: () -> Unit,
    onLogoutClick: () -> Unit,
    viewModel: NewsViewModel = koinViewModel()
) {
    val context = LocalContext.current
    ObserveAsEvents(flow = viewModel.channel) { event ->
        when (event) {
            is NewsEvent.ErrorEvent -> {
                Toast.makeText(context, event.value.asString(context), Toast.LENGTH_LONG).show()
            }

            is NewsEvent.LogOutEvent -> {
                Toast.makeText(context, event.value.asString(context), Toast.LENGTH_LONG).show()
            }
        }
    }
    NewsScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                is NewsAction.OnLoginClick -> onLoginClick()
                is NewsAction.OnLogoutClick -> {
                    viewModel.onAction(action)
                    onLogoutClick()
                }

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
                    IconButton(
                        onClick =
                        {
                            onAction(NewsAction.OnLogoutClick)
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_logout_24),
                            contentDescription = "Log out"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (!state.isLoggedIn) {
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
            if (state.error == null) {
                ListDetailLayout(
                    modifier = Modifier.fillMaxSize(),
                    paddingValues = paddingValues,
                    state = state,
                    onAction = {
                        onAction(it)
                    }
                )
            } else {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Error occurred:")
                    SpacerVerM()
                    Text(textAlign = TextAlign.Center, text = state.error.asComposeString())
                    SpacerVerL()
                    Button(
                        onClick = {
                            onAction(NewsAction.OnRefresh)
                        }
                    ) {
                        Text(text = "Fetch news again")
                    }
                }
            }
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
