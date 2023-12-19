package com.example.jelajahiapp.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.jelajahiapp.ui.screen.authorization.ChangePasswordScreen
import com.example.jelajahiapp.ui.screen.authorization.LoginScreen
import com.example.jelajahiapp.ui.screen.authorization.SignupScreen
import com.example.jelajahiapp.ui.screen.cultural.CulturalScreen
import com.example.jelajahiapp.ui.screen.cultural.DetailCulturalScreen
import com.example.jelajahiapp.ui.screen.explorer.DetailExplorerScreen
import com.example.jelajahiapp.ui.screen.explorer.ExplorerScreen
import com.example.jelajahiapp.ui.screen.favorite.FavoriteScreen
import com.example.jelajahiapp.ui.screen.home.DetailHomeExplorerScreen
import com.example.jelajahiapp.ui.screen.home.HomeScreen
import com.example.jelajahiapp.ui.screen.recommendation.RecommendationActivity
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

        composable(route = Screen.ChangePassword.route) {
            ChangePasswordScreen(navController = navController, onBackClick = { navController.navigateUp() })
        }

        composable(route = Screen.Home.route) {
            HomeScreen(
                navController = navController,
                navigateToDetail = { culturalID ->
                    navController.navigate(Screen.Detail.createRoute(culturalID))
                },
                navigateToDetailExplorer = { placeId ->
                    navController.navigate(Screen.DetailHomeExplorer.createRoute(placeId))
                }
            )
        }

        composable(route = Screen.Cultural.route) {
            CulturalScreen(navController = navController, navigateToDetail = { culturalID ->
                navController.navigate(Screen.Detail.createRoute(culturalID)) }, navigateBack = { navController.navigateUp() })
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("culturalId") { type = NavType.LongType }),
        ) {
            val culturalId = it.arguments?.getLong("culturalId") ?: 1L
            DetailCulturalScreen(culturalId = culturalId) {
                navController.navigateUp()
            }
        }

        composable(
            route = Screen.DetailHomeExplorer.route,
            arguments = listOf(navArgument("placeId") { type = NavType.StringType }),
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("placeId") ?: "1"
            DetailHomeExplorerScreen(
                placeId = id,
            ) {
                navController.navigateUp()
            }
        }


        composable(route = Screen.RecommendationActivity.route) {
            RecommendationActivity()
        }


        composable(route = Screen.AddCommunity.route) {
//            CommunityScreen(navController = navController)
        }

        composable(route = Screen.Explorer.route) {
            ExplorerScreen(navController = navController,
                navigateToDetailExplorerScreen = { placeId ->
                    navController.navigate(Screen.DetailExplorer.createRoute(placeId))
                })
        }

        composable(
            route = Screen.DetailExplorer.route,
            arguments = listOf(navArgument("placeId") { type = NavType.StringType }),
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("placeId") ?: "1"
            DetailExplorerScreen(
                placeId = id,
            ) {
                navController.navigateUp()
            }
        }

        composable(route = Screen.Favorite.route) {
            FavoriteScreen(navController = navController, navigateToDetail = { placeId ->
                navController.navigate(Screen.DetailExplorer.createRoute(placeId)) })
        }

        composable(
            route = Screen.DetailExplorer.route,
            arguments = listOf(navArgument("placeId") { type = NavType.StringType }),
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("placeId") ?: "1"
            DetailExplorerScreen(
                placeId = id,
            ) {
                navController.navigateUp()
            }
        }


//        composable(route = Screen.Register.route) {
//            AddCommunityScreen(onCommunitySubmited)
//        }
    }
}