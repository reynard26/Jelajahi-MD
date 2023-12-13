package com.example.jelajahiapp.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jelajahiapp.R
import com.example.jelajahiapp.navigation.NavItem
import com.example.jelajahiapp.navigation.Screen
import com.example.jelajahiapp.ui.theme.green87
import com.example.jelajahiapp.ui.theme.purple100

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
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
                icon = R.drawable.baseline_travel_explore_24,
                screen = Screen.Favorite,
                contentDescription = stringResource(R.string.menu_explore)
            ),
            NavItem(
                title = stringResource(R.string.camera),
                icon = R.drawable.baseline_photo_camera_24,
                screen = Screen.Profile,
                contentDescription = stringResource(R.string.menu_camera)
            ),
            NavItem(
                title = stringResource(R.string.community),
                icon = R.drawable.baseline_comment_24,
                screen = Screen.Profile,
                contentDescription = stringResource(R.string.menu_community)
            ),
            NavItem(
                title = stringResource(R.string.liked),
                icon = R.drawable.baseline_favorite_border_24,
                screen = Screen.Profile,
                contentDescription = stringResource(R.string.menu_liked)
            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        tint = if (currentRoute == item.screen.route) purple100 else Color.White // Adjust the icon color based on selection
                    )
                },
                label = { Text(
                    text = item.title,
                    color = if (currentRoute == item.screen.route) Color.White else Color.White // Adjust the title color based on selection
                ) },
                alwaysShowLabel = false,
                selected = currentRoute == item.screen.route,
                onClick = {
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