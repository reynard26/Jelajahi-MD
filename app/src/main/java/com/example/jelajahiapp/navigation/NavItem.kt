package com.example.jelajahiapp.navigation

data class NavItem(
    val title: String,
    val icon: Int,
    val screen: Screen,
    val contentDescription: String,
    val onClick: (() -> Unit)? = null
)