package com.jozefv.newsdata.news.presentation.components.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.jozefv.newsdata.R
import com.jozefv.newsdata.core.presentation.SpacerVerL
import com.jozefv.newsdata.core.presentation.SpacerVerM
import com.jozefv.newsdata.news.presentation.components.NewsInfoRowSection
import com.jozefv.newsdata.news.presentation.mappers.ResultUiParcelize

@Composable
fun NewsDetail(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    resultUi: ResultUiParcelize,
    onShareClick: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
    ) {
        SubcomposeAsyncImage(
            modifier = modifier
                .heightIn(min = 200.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.FillBounds,
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
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.default_news_image),
                        contentDescription = "Default news image"
                    )
                }
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 16.dp,
                    vertical = 24.dp
                )
        ) {
            NewsInfoRowSection(
                modifier = Modifier.fillMaxWidth(),
                leftText = resultUi.publishedDate,
                rightText = "Source: ${resultUi.sourceName}",
                icon = {
                    IconButton(
                        onClick = {
                            onShareClick()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = Icons.Default.Share.name
                        )
                    }
                }
            )
            SpacerVerL()
            Text(
                style = MaterialTheme.typography.headlineMedium,
                text = resultUi.title
            )
            SpacerVerM()
            Text(
                style = MaterialTheme.typography.bodyMedium,
                text = resultUi.description
            )
        }
    }
}

@Preview
@Composable
private fun NewsDetailPreview() {
    MaterialTheme {
        NewsDetail(
            paddingValues = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
            resultUi = ResultUiParcelize(
                title = "Hockey games are starting",
                description = "This will be the best games ever! Come and support us!",
                publishedDate = "24.8.2024 16:22:38",
                sourceName = "The games",
                articleLink = "",
                imageUrl = "",
                sourceUrl = ""
            ),
            onShareClick = {}
        )
    }
}