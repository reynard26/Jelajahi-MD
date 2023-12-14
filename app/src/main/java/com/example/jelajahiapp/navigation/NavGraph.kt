package com.example.jelajahiapp.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.jelajahiapp.ui.screen.authorization.LoginScreen
import com.example.jelajahiapp.ui.screen.authorization.SignupScreen
import com.example.jelajahiapp.ui.screen.cultural.DetailCulturalScreen
import com.example.jelajahiapp.ui.screen.home.HomeScreen
import com.example.jelajahiapp.ui.screen.splash.OnBoardingScreen
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Welcome.route) {
            OnBoardingScreen(navController = navController)
        }
        composable(route = Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screen.Register.route) {
            SignupScreen(navController = navController)
        }

        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController,
                navigateToDetail = { culturalID ->
                    navController.navigate(Screen.Detail.createRoute(culturalID))
                })
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("culturalId") { type = NavType.LongType }),
        ) {
            val id = it.arguments?.getLong("culturalId") ?: 1L
            DetailCulturalScreen(
                culturalId = id
            ) {
                navController.navigateUp()
            }
        }

        composable(route = Screen.AddCommunity.route) {
//            CommunityScreen(navController = navController)
        }

//        composable(route = Screen.Register.route) {
//            AddCommunityScreen(onCommunitySubmited)
//        }
    }
}