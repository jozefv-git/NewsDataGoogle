package com.jozefv.newsdata.news.presentation.components.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.jozefv.newsdata.core.presentation.SpacerVerL
import com.jozefv.newsdata.core.presentation.SpacerVerM
import com.jozefv.newsdata.news.presentation.components.NewsInfoRowSection
import com.jozefv.newsdata.news.presentation.mappers.ResultUiParcelize

@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    resultUi: ResultUiParcelize,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        content = {
            SubcomposeAsyncImage(
                modifier = modifier
                    .fillMaxWidth(),
                model = resultUi.imageUrl,
                contentDescription = null,
                loading = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp))
                    }
                },
                error = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        SpacerVerM()
                        Text(text = "Couldn't load image.")
                    }
                }
            )
            SpacerVerM()
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    style = MaterialTheme.typography.bodyLarge,
                    text = resultUi.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                SpacerVerM()
                Text(
                    style = MaterialTheme.typography.headlineSmall,
                    text = resultUi.description,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                SpacerVerL()
                NewsInfoRowSection(
                    modifier = Modifier.fillMaxWidth(),
                    leftText = resultUi.publishedDate,
                    rightText = "Source: ${resultUi.sourceName}"
                )
            }
        }
    )
}

@Preview
@Composable
private fun NewsCardPreview() {
    MaterialTheme {
        NewsCard(
            resultUi = ResultUiParcelize(
                title = "Hockey games are starting",
                description = "This will be the best games ever! Come and support us!",
                publishedDate = "24.8.2024 16:22:38",
                sourceName = "The games",
                articleLink = "",
                imageUrl = "",
                sourceUrl = ""
            )
        )
    }
}