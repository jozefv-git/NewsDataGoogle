package com.jozefv.newsdata

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jozefv.newsdata.auth.presentation.LoginScreenRoot
import com.jozefv.newsdata.news.presentation.NewsScreenRoot
import com.jozefv.newsdata.profile.presentation.ProfileScreenRoot
import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object News

@Serializable
object Profile

@Composable
fun NavigationRoot(navHostController: NavHostController, isLoggedIn: Boolean) {
    NavHost(navController = navHostController, startDestination = if (isLoggedIn) News else Login) {
        composable<Login> {
            LoginScreenRoot(
                onSkip = {
                    navHostController.navigate(route = News)
                }, onLoginSuccess = {
                    navHostController.navigate(route = News) {
                        // After successful login - we don't want to go back to login screen if back-pressed
                        popUpTo(Login) {
                            inclusive = true
                        }
                    }
                })
        }
        composable<News> {
            NewsScreenRoot(
                onProfileCLick = {
                    navHostController.navigate(route = Profile) {
                        popUpTo(Profile)
                        {
                            inclusive = true
                        }
                    }
                },
                onLoginClick = {
                    navHostController.navigate(route = Login) {
                        popUpTo(Login) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<Profile> {
            ProfileScreenRoot(onLogoutClick = {
                navHostController.navigate(route = Login) {
                    popUpTo(News) {
                        inclusive = true

                    }
                }
            })
        }
    }
}