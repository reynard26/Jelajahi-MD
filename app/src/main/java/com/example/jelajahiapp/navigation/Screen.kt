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
    object Detail : Screen("home/{culturalId}") {
        fun createRoute(culturalId: Long) = "home/$culturalId"
    }
}