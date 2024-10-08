package com.jozefv.newsdata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.jozefv.newsdata.core.presentation.ui.theme.NewsDataTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModel<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // SplashScreen must be defined before content is set
        installSplashScreen().apply {
            // As long as condition is true, we will kept on showing our splash screen
            setKeepOnScreenCondition {
                mainViewModel.state.isAuthenticating
            }
        }
        enableEdgeToEdge()
        setContent {
            NewsDataTheme {
                if (!mainViewModel.state.isAuthenticating) {
                    NavigationRoot(
                        navHostController = rememberNavController(),
                        isLoggedIn = mainViewModel.state.isLoggedIn
                    )
                }
            }
        }
    }
}