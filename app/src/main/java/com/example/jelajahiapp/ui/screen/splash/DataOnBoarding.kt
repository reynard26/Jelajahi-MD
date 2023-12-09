package com.example.jelajahiapp.ui.screen.splash

import androidx.annotation.DrawableRes
import com.example.jelajahiapp.R

sealed class DataOnBoarding (
    @DrawableRes
    val image: Int,
    val heading: String,
    val title: String,
    val description: String
){
    object First : DataOnBoarding(
        image = R.drawable.onboarding_1,
        heading = "#Culture",
        title = "Uncover Indonesia's Hidden Treasures",
        description = "Snap, discover, and explore Indonesia's culture with JelajahI's image recognition. Get instant mini-dictionary insights on cultural items."
    )

    object Second : DataOnBoarding(
        image = R.drawable.onboarding_2,
        heading = "#Travel",
        title = "Your Personalized Journeys Await",
        description = "Turn cultural discoveries into personalized travel recommendations with JelajahI. Your unique journey starts here."
    )

    object Third : DataOnBoarding(
        image = R.drawable.onboarding_3,
        heading = "#Community",
        title = "Connect through  #Penjelajah Stories",
        description = "Share, connect, and explore with fellow #Penjelajah. Join our community, capture cultural moments, and make every story a global adventure."
    )
}