package com.example.jelajahiapp.navigation

sealed class Screen(val route: String)
{
    object Welcome : Screen("onboarding")

    object Login : Screen("login")

    object Register : Screen("register")
    object Home : Screen("home")
    object Profile : Screen("creator profile")
    object Favorite : Screen("favorite")
    object Detail : Screen("home/{dogId}") {
        fun createRoute(dogId: Long) = "home/$dogId"
    }
}