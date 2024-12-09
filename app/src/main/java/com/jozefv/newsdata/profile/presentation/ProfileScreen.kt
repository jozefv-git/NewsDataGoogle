package com.jozefv.newsdata.profile.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.jozefv.newsdata.core.presentation.components.GoogleButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.jozefv.newsdata.R
import com.jozefv.newsdata.core.presentation.SpacerVerL
import com.jozefv.newsdata.core.presentation.components.CustomScaffold
import com.jozefv.newsdata.core.presentation.components.CustomToolBar
import com.jozefv.newsdata.news.presentation.components.CustomAlertDialog
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreenRoot(
    onLogoutClick: () -> Unit,
    viewModel: ProfileScreenViewModel = koinViewModel(),
) {
    ProfileScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                is ProfileAction.OnLogout -> {
                    viewModel.onAction(action)
                    onLogoutClick()
                }

                else -> viewModel.onAction(action)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileScreen(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit,
) {
    var isDialogVisible by remember {
        mutableStateOf(false)
    }
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = rememberTopAppBarState()
    )
    CustomScaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehaviour.nestedScrollConnection),
        topAppBar = {
            CustomToolBar(
                scrollBehavior = scrollBehaviour,
                title = "Profile",
                trailingContent = {
                    IconButton(
                        onClick =
                        {
                            isDialogVisible = true
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 32.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (state.isLoggedWithGoogle) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Fit,
                    model = state.profilePictureUrl,
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
                                contentDescription = "Default profile image"
                            )
                        }
                    }
                )
                SpacerVerL()
                Text(text = "Display name:")
                Text(text = state.displayName ?: "Display name doesn't exist")
            } else {
                Column(
                    Modifier.fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 32.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "Google profile is visible only when logged with google account"
                    )
                    SpacerVerL()
                    GoogleButton(onAction = {
                        onAction(ProfileAction.OnLogin(it))
                    })
                }
            }
        }
    }

    if (isDialogVisible) {
        CustomAlertDialog(
            dialogTitle = "Logout",
            dialogText = "Are you sure you want to logout?",
            dialogIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_logout_24),
                    contentDescription = "Log out"
                )
            },
            onDismiss = { isDialogVisible = false },
            onConfirm = {
                onAction(ProfileAction.OnLogout)
                isDialogVisible = false
            }
        )
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
// Add theme here
    MaterialTheme {
        ProfileScreen(
            state = ProfileState(),
            onAction = {}
        )
    }
}
