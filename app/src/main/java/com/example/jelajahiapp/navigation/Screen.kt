package com.example.jelajahiapp.navigation

sealed class Screen(val route: String)
{
    object Welcome : Screen("onboarding")

    object Login : Screen("login")
    object Register : Screen("register")

    object Home : Screen("home")

    object ChangePassword : Screen("ChangePassword")

    object Community : Screen("Community")
    object AddCommunity : Screen("addcommunity")

    object RecommendationActivity : Screen("recommendationActivity")



    object Cultural : Screen("cultural")

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

    object Favorite : Screen("favorite")
}