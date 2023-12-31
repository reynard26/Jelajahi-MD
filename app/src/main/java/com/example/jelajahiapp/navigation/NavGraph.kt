package com.example.jelajahiapp.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.jelajahiapp.navigation.Screen.RecommendationResult.ARG_NAME
import com.example.jelajahiapp.ui.screen.authorization.ChangePasswordScreen
import com.example.jelajahiapp.ui.screen.authorization.LoginScreen
import com.example.jelajahiapp.ui.screen.authorization.SignupScreen
import com.example.jelajahiapp.ui.screen.community.AddCommunityScreen
import com.example.jelajahiapp.ui.screen.community.CommunityScreen
import com.example.jelajahiapp.ui.screen.cultural.CulturalScreen
import com.example.jelajahiapp.ui.screen.cultural.DetailCulturalScreen
import com.example.jelajahiapp.ui.screen.explorer.DetailExplorerScreen
import com.example.jelajahiapp.ui.screen.explorer.ExplorerScreen
import com.example.jelajahiapp.ui.screen.favorite.FavoriteScreen
import com.example.jelajahiapp.ui.screen.home.DetailHomeExplorerScreen
import com.example.jelajahiapp.ui.screen.home.HomeScreen
import com.example.jelajahiapp.ui.screen.recommendation.DetailRecommendationScreen
import com.example.jelajahiapp.ui.screen.recommendation.RecommendationScreen
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
            RecommendationScreen(navController = navController)
        }

        composable(Screen.RecommendationResult.route + "/{$ARG_NAME}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString(ARG_NAME)
            DetailRecommendationScreen(navController = navController, name = name,
                navigateToDetailExplorerScreen = { placeId ->
                    navController.navigate(Screen.DetailExplorer.createRoute(placeId))
                },
                navigateBack = {
                navController.navigate(Screen.RecommendationActivity.route) {
                    popUpTo(Screen.RecommendationActivity.route) {
                        inclusive = true
                    }
                }
            })
        }
//        composable(route = Screen.RecommendationResult.route) {
////            DetailRecommendationContent(navController = navController)
//        }



        composable(route = Screen.Community.route) {
            CommunityScreen(navController = navController)
        }

        composable(route = Screen.AddCommunity.route) {
            AddCommunityScreen(navController = navController)
        }

//        composable(route = "${Screen.EditCommunity.route}/{communityPost}") { backStackEntry ->
//            val communityPostString = backStackEntry.arguments?.getString("communityPost")
//            val decodedCommunityJson = Uri.decode(communityPostString.orEmpty())
//
//            var communityPost by remember { mutableStateOf<ResponseCommunity?>(null) }
//            var isError by remember { mutableStateOf(false) }
//
//            DisposableEffect(decodedCommunityJson) {
//                try {
//                    communityPost = Gson().fromJson(decodedCommunityJson, ResponseCommunity::class.java)
//                } catch (e: JsonSyntaxException) {
//                    isError = true
//                    Log.e("EditCommunity", "Error decoding community post: ${e.message}")
//                }
//
//                onDispose { }
//            }
//
//            if (isError) {
//                // Handle the case where decoding or parsing fails
//                Text("Error decoding community post")
//            } else {
//                // Render your EditCommunityScreen with communityPost
//                if (communityPost != null) {
//                    EditCommunityScreen(navController = navController, communityPost = communityPost!!)
//                } else {
//                    // Handle the case where communityPost is null
//                    Text("Community post is null")
//                }
//            }
//        }

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