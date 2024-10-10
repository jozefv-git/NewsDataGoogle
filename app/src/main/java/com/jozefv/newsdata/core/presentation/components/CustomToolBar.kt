@file:OptIn(ExperimentalMaterial3Api::class)

package com.jozefv.newsdata.core.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun CustomToolBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    title: String,
    leadingContent: @Composable () -> Unit = {},
    trailingContent: @Composable () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        title = {
            Text(text = title)
        },
        navigationIcon = {
            leadingContent()
        },
        actions = {
            trailingContent()
        }
    )
}

@Preview
@Composable
private fun CustomToolBarPreview() {
    CustomToolBar(
        title = "Best news",
        leadingContent = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = Icons.Default.ArrowBack.name
            )
        },
        trailingContent = {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = Icons.Default.AccountCircle.name
            )
        }
    )
}