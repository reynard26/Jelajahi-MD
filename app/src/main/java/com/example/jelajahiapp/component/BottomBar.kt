package com.example.jelajahiapp.component

import android.content.Intent
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jelajahiapp.R
import com.example.jelajahiapp.navigation.NavItem
import com.example.jelajahiapp.navigation.Screen
import com.example.jelajahiapp.ui.screen.recommendation.RecommendationActivity
import com.example.jelajahiapp.ui.theme.green87
import com.example.jelajahiapp.ui.theme.purple100

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    NavigationBar(
        modifier = modifier,
        containerColor = green87

    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavItem(
                title = stringResource(R.string.home),
                icon = R.drawable.baseline_home_24,
                screen = Screen.Home,
                contentDescription = stringResource(R.string.menu_home)
            ),
            NavItem(
                title = stringResource(R.string.explore),
                icon = R.drawable.baseline_explore_24,
                screen = Screen.Explorer,
                contentDescription = stringResource(R.string.menu_explore)
            ),
            NavItem(
                title = stringResource(R.string.camera),
                icon = R.drawable.baseline_photo_camera_24,
                screen = Screen.RecommendationActivity,
                contentDescription = stringResource(R.string.menu_camera),
                onClick = {
                    context.startActivity(Intent(context, RecommendationActivity::class.java))
                }
            ),
            NavItem(
                title = stringResource(R.string.forum),
                icon = R.drawable.baseline_connect_without_contact_24,
                screen = Screen.Community,
                contentDescription = stringResource(R.string.menu_forum)
            ),
            NavItem(
                title = stringResource(R.string.favorite),
                icon = R.drawable.baseline_favorite_24,
                screen = Screen.Favorite,
                contentDescription = stringResource(R.string.menu_favorite)
            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        tint = if (currentRoute == item.screen.route) purple100 else Color.White
                    )
                },
                label = { Text(
                    text = item.title,
                    color = if (currentRoute == item.screen.route) Color.White else Color.White
                ) },
                alwaysShowLabel = false,
                selected = currentRoute == item.screen.route,
                onClick = {
                    item.onClick?.invoke()
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor= Color.White)
            )
        }
    }
}