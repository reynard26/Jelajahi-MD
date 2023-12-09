package com.example.jelajahiapp.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
//        composable(route = Screen.Welcome.route) {
//            WelcomeScreen(navController = navController)
//        }
//        composable(route = Screen.Home.route) {
//            HomeScreen()
//        }
    }
}