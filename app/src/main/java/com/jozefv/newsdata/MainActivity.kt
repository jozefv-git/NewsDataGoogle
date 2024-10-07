package com.jozefv.newsdata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jozefv.newsdata.auth.presentation.LoginScreenRoot
import com.jozefv.newsdata.core.presentation.ui.theme.NewsDataTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsDataTheme {
                LoginScreenRoot(onLoginSuccess = { /*TODO*/ })
            }
        }
    }
}