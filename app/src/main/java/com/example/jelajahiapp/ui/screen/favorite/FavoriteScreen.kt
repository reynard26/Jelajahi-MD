package com.example.jelajahiapp.ui.screen.favorite

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jelajahiapp.R
import com.example.jelajahiapp.component.BottomBar
import com.example.jelajahiapp.data.Result
import com.example.jelajahiapp.data.ViewModelFactory
import com.example.jelajahiapp.data.location.PlaceResult
import com.example.jelajahiapp.navigation.Screen
import com.example.jelajahiapp.ui.theme.fonts
import com.example.jelajahiapp.ui.theme.purple100

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavoriteScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    navigateToDetail: (String) -> Unit,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Detail.route) {
                BottomBar(navController)
            }
        },
    ) {
        viewModel.uiState.collectAsState(initial = Result.Loading).value.let { uiState ->
            when (uiState) {
                is Result.Loading -> {
                    viewModel.getallFavoritePlaces()
                }

                is Result.Success -> {
                    FavoriteContent(
                        locations = uiState.data,
                        navigateToDetail = navigateToDetail,
                        modifier = modifier
                    )
                }
                is Result.Error -> {}
                else -> {}
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteContent(
    locations: List<PlaceResult>,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier
) {
    val listState = rememberLazyListState()
    Box(modifier = Modifier.fillMaxSize().padding(15.dp)) {
        Column(Modifier.padding(0.dp, 0.dp, 0.dp, 60.dp)) {
            Text(
                text = stringResource(id = R.string.liked),
                fontFamily = fonts,
                fontWeight = FontWeight.ExtraBold,
                color = purple100,
                fontSize = 30.sp,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column {
                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(bottom = 8.dp),
                    modifier = modifier
                        .testTag("FavoritePlaceList")
                ) {
                    items(locations, key = { it.placeId }) { itemsPlaces ->
                        Spacer(modifier = Modifier.height(10.dp))
                        FavoriteItem(
                            photos = itemsPlaces.photos,
                            name = itemsPlaces.name,
                            vicinity = itemsPlaces.vicinity,
                            geometry = itemsPlaces.geometry,
                            modifier = Modifier
                                .clickable {
                                    navigateToDetail(itemsPlaces.placeId)
                                }
                                .animateItemPlacement(tween(durationMillis = 500))
                        )
                    }
                }
            }
        }
    }
}