package com.jozefv.newsdata.news.presentation.components.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.jozefv.newsdata.core.presentation.SpacerVerL
import com.jozefv.newsdata.core.presentation.SpacerVerM
import com.jozefv.newsdata.news.domain.ResultUi
import com.jozefv.newsdata.news.presentation.components.NewsInfoRowSection

@Composable
fun NewsDetail(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    resultUi: ResultUi,
    onShareClick: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
    ) {
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
                    CircularProgressIndicator()
                }
            },
            error = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    SpacerVerM()
                    Text(text = "Couldn't load image.")
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