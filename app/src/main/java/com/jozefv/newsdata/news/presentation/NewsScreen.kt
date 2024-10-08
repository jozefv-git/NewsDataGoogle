package com.jozefv.newsdata.news.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jozefv.newsdata.core.presentation.SpacerVerL
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsScreenRoot(
    onLoginClick: () -> Unit,
    viewModel: NewsViewModel = koinViewModel()
) {
    NewsScreen(
        state = viewModel.state,
        onAction = { action ->
            when(action){
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
    Column(
        modifier = Modifier.fillMaxSize(),
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
