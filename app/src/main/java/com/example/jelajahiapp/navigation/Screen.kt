package com.example.jelajahiapp.navigation

sealed class Screen(val route: String)
{
    object Welcome : Screen("onboarding")

    object Login : Screen("login")

    object Home : Screen("home")

    object AddCommunity : Screen("addcommunity")

    object RecommendationActivity : Screen("recommendationActivity")



    object Cultural : Screen("cultural")

    object Register : Screen("register")
    object Profile : Screen("creator profile")
    object Favorite : Screen("favorite")

    object Detail : Screen("detail/{culturalId}") {
        fun createRoute(culturalId: Long) = "detail/$culturalId"
    }

    object DetailHomeExplorer : Screen("homeExplorer/{placeId}") {
        fun createRoute(placeId: String) = "homeExplorer/$placeId"
    }

    object Explorer : Screen("Explorer")
    object DetailExplorer : Screen("Explorer/{placeId}") {
        fun createRoute(placeId: String) = "Explorer/$placeId"
    }
}