package com.example.jelajahiapp.ui.screen.explorer

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.jelajahiapp.R
import com.example.jelajahiapp.component.BottomBar
import com.example.jelajahiapp.data.ViewModelFactory
import com.example.jelajahiapp.data.location.PlaceResult
import com.example.jelajahiapp.navigation.Screen
import com.example.jelajahiapp.ui.screen.home.truncate
import com.example.jelajahiapp.ui.theme.Shapes
import com.example.jelajahiapp.ui.theme.fonts
import com.example.jelajahiapp.ui.theme.green87
import com.example.jelajahiapp.ui.theme.purple100

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ExplorerScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: ExplorerViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current))
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var searchText by remember { mutableStateOf("") }

    val filteredLocations by viewModel.filteredLocations.collectAsState()

    val isLoading by viewModel.isLoading.collectAsState()

    val lastItemPosition = remember { mutableStateOf(0) }

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
                    text = stringResource(id = R.string.explorer),
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
                        viewModel.filterLocations(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                )

                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                LocationList(locations = filteredLocations, onLastItemLaidOut = { index ->
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
fun LocationList(locations: List<PlaceResult>, onLastItemLaidOut: (Int) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(1.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = rememberLazyGridState()
    ) {
        itemsIndexed(locations) { index, location ->
            DestinationItem(location = location)
            if (index == locations.size - 1) {
                onLastItemLaidOut(index)
            }
        }
    }
}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun DestinationItem(
    location: PlaceResult,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .border(BorderStroke(1.dp, color = green87), shape = Shapes.large)
            .width(170.dp)
            .padding(8.dp), // Ensure the Column takes the full width
        horizontalAlignment = Alignment.Start
    ) {
        location.photos?.firstOrNull()?.let { photo ->
            Image(
                painter = rememberImagePainter(data = buildPhotoUrl(photo.photoReference, 400)),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(5.dp, 10.dp, 5.dp, 7.dp)
                    .width(155.dp)
                    .height(120.dp)
                    .clip(Shapes.large)
            )
        }

        Text(
            text = location.name.truncate(14),
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 15.sp,
            modifier = Modifier
                .padding(5.dp, 0.dp, 5.dp, 0.dp)
        )
        Row (modifier = Modifier
            .padding(2.dp,2.dp, 2.dp, 10.dp)){
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = null,
                tint = purple100,
                modifier = modifier
                    .size(20.dp)
            )

            Text(
                text = (location.vicinity).truncate(16),
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 13.sp
            )
        }
    }
}


fun buildPhotoUrl(photoReference: String, maxWidth: Int): String {
    val apiKey = "AIzaSyDikJA_zqvlFv4heu7UnWMht7j1JOTpiN8"
    val baseUrl = "https://maps.googleapis.com/maps/api/place/photo"
    return "$baseUrl?maxwidth=$maxWidth&photo_reference=$photoReference&key=$apiKey"
}

fun String.truncate(maxCharacters: Int): String {
    return if (length > maxCharacters) {
        "${take(maxCharacters)}..."
    } else {
        this
    }
}