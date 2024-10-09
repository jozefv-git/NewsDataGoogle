@file:OptIn(ExperimentalMaterial3Api::class)

package com.jozefv.newsdata.core.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CustomScaffold(
    modifier: Modifier = Modifier,
    topAppBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = topAppBar
    ) { padding ->
        content(padding)
    }
}

@Preview
@Composable
private fun CustomScaffoldPreview() {
    CustomScaffold(
        topAppBar = {
            CustomToolBar(title = "Custom toolbar")
        }
    ) {
        // Possible content
    }
}