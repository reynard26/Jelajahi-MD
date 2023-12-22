package com.example.jelajahiapp.ui.screen.explorer

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jelajahiapp.BuildConfig
import com.example.jelajahiapp.R
import com.example.jelajahiapp.component.BottomBar
import com.example.jelajahiapp.data.ViewModelFactory
import com.example.jelajahiapp.data.location.PlaceResult
import com.example.jelajahiapp.navigation.Screen
import com.example.jelajahiapp.ui.theme.fonts
import com.example.jelajahiapp.ui.theme.purple100

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun ExplorerScreen(
    navController: NavHostController,
    viewModel: ExplorerViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current)),
    navigateToDetailExplorerScreen: (String) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var searchText by remember { mutableStateOf("") }


    val filteredLocations by viewModel.filteredLocations.collectAsState()

    val lastItemPosition = remember { mutableStateOf(0) }

    DisposableEffect(Unit) {
        viewModel.getExplore() // Load the first location
        onDispose { }
    }

    LaunchedEffect(lastItemPosition.value) {
        if (lastItemPosition.value != 0) {
            viewModel.getExplore()
        }
    }

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Detail.route) {
                BottomBar(navController)
            }
        },
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(15.dp)) {
            Column(Modifier.padding(0.dp, 0.dp, 0.dp, 70.dp)) {
                Text(
                    text = stringResource(id = R.string.explore),
                    fontFamily = fonts,
                    fontWeight = FontWeight.ExtraBold,
                    color = purple100,
                    fontSize = 30.sp,
                )
                Text(
                    text = stringResource(id = R.string.discover_indonesiaan),
                    fontFamily = fonts,
                    color = Color.Black,
                    fontSize = 15.sp,
                )
                SearchBar(
                    query = searchText,
                    onQueryChange = {
                        searchText = it
                        viewModel.updateSearchQuery(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                )

                if (viewModel.isLoading.value) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                LocationList(locations = filteredLocations,
                    navigateToDetailExplorerScreen = navigateToDetailExplorerScreen,
                    onLastItemLaidOut = { index ->
                    if (index == filteredLocations.size - 1) {
                        lastItemPosition.value = index
                    }
                })

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun LocationList(
    locations: List<PlaceResult>,
    onLastItemLaidOut: (Int) -> Unit,
    viewModel: ExplorerViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current)),
    navigateToDetailExplorerScreen: (String) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(130.dp),
        contentPadding = PaddingValues(1.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = rememberLazyGridState()
    ) {
        itemsIndexed(locations) { index, location ->
            DestinationItem(location = location, modifier = Modifier.clickable {
                viewModel.getLocationDetails(location.placeId)
                navigateToDetailExplorerScreen(location.placeId)
            })
            if (index == locations.size - 1) {
                onLastItemLaidOut(index)
            }
        }
    }
}

private const val API_KEY = BuildConfig.API_KEY
fun buildPhotoUrl(photoReference: String, maxWidth: Int): String {
    val apiKey = API_KEY
    val baseUrl = "https://maps.googleapis.com/maps/api/place/photo"
    return "$baseUrl?maxwidth=$maxWidth&photo_reference=$photoReference&key=$apiKey"
}