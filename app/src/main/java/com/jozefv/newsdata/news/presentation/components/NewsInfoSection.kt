package com.jozefv.newsdata.news.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.jozefv.newsdata.core.presentation.SpacerHorM

@Composable
fun NewsInfoRowSection(
    modifier: Modifier = Modifier,
    leftText: String = "",
    rightText: String = "",
    icon: @Composable () -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            style = MaterialTheme.typography.displayMedium,
            text = leftText
        )
        Column(
            modifier = Modifier.fillMaxWidth(.85f),
            horizontalAlignment = Alignment.End) {
            Text(
                style = MaterialTheme.typography.displayMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = rightText
            )
            SpacerHorM()
            icon()
        }
    }
}

@Preview
@Composable
private fun NewsInfoRowSectionPreview() {
    MaterialTheme {
        NewsInfoRowSection(
            leftText = "10-08-2024 10:19:00",
            rightText = "Long source New Your Times and even more"
        )
    }
}