package com.example.jelajahiapp.ui.screen.recommendation

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.jelajahiapp.R
import com.example.jelajahiapp.data.Result
import com.example.jelajahiapp.data.ViewModelFactory
import com.example.jelajahiapp.data.location.PlaceResult
import com.example.jelajahiapp.ui.screen.explorer.DestinationItem
import com.example.jelajahiapp.ui.screen.explorer.ExplorerViewModel
import com.example.jelajahiapp.ui.theme.Shapes
import com.example.jelajahiapp.ui.theme.fonts
import com.example.jelajahiapp.ui.theme.green87
import com.example.jelajahiapp.ui.theme.grey40
import com.example.jelajahiapp.ui.theme.purple100

@Composable
fun DetailRecommendationScreen(
    name: String?,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: RecommendationViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    navigateToDetailExplorerScreen: (String) -> Unit,
    navigateBack: () -> Unit,
) {
    LaunchedEffect(name) {
        if (name != null) {
            viewModel.getCulturalName(name)
            viewModel.getRecommendationList(convertToLowerCaseNoSpace(name))
        }
    }

    if (name != null) {
        Log.d("kecilkah",convertToLowerCaseNoSpace(name))
    }
    DisposableEffect(Unit) {
        if (name != null) {
            viewModel.getRecommendationList(convertToLowerCaseNoSpace(name))
        }
        onDispose { }
    }

    viewModel.uiState.collectAsState().value.let { uiState ->
        when (uiState) {
            is Result.Success -> {
                DetailContentRecommendation(
                    image = uiState.data.image,
                    culturalName = uiState.data.culturalName,
                    culturalType = uiState.data.culturalType,
                    location = uiState.data.location,
                    description = uiState.data.description,
                    onBackClick = navigateBack,
                    navigateToDetailExplorerScreen = navigateToDetailExplorerScreen,
                )
            }
            else -> {}
        }
    }

    // Logging to check the content of locations
    val locations by viewModel.locations.collectAsState()
    println("Locations: $locations")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContentRecommendation(
    @DrawableRes image: Int,
    culturalName: String,
    culturalType: String,
    location: String,
    description: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    navigateToDetailExplorerScreen: (String) -> Unit,
    viewModel: RecommendationViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current)),
) {
    val locations by viewModel.locations.collectAsState()
    val context = LocalContext.current

    // State to track whether the button is clicked or not
    var isButtonClicked by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { onBackClick() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                title = {
                    Text(
                        text = culturalName,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (isButtonClicked) {
                LocationListRecommendation(
                    locations = locations,
                    navigateToDetailExplorerScreen = navigateToDetailExplorerScreen,
                )
            } else {
                Image(
                    painter = painterResource(image),
                    contentDescription = culturalName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(350.dp)
                        .fillMaxWidth()
                )
                Box(
                    modifier = Modifier
                        .absolutePadding(0.dp, 250.dp, 0.dp, 0.dp)
                        .padding(15.dp)
                        .background(color = Color.White, Shapes.large)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        item {
                            Text(
                                text = culturalName,
                                overflow = TextOverflow.Ellipsis,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 25.sp,
                                fontFamily = fonts,
                                color = purple100
                            )
                            Text(
                                text = culturalType,
                                overflow = TextOverflow.Ellipsis,
                                fontWeight = FontWeight.SemiBold,
                                color = grey40,
                                fontFamily = fonts,
                                fontSize = 13.sp,
                                modifier = Modifier
                                    .padding(0.dp, 2.dp, 0.dp, 10.dp)
                            )

                            Row {
                                Icon(
                                    imageVector = Icons.Default.Place,
                                    contentDescription = null,
                                    tint = grey40,
                                    modifier = modifier
                                        .size(20.dp)
                                )

                                Text(
                                    text = location,
                                    overflow = TextOverflow.Ellipsis,
                                    color = grey40,
                                    fontFamily = fonts,
                                    fontSize = 14.sp,
                                    modifier = Modifier
                                        .padding(0.dp, 2.dp, 0.dp, 10.dp)
                                )
                            }
                            Text(
                                text = stringResource(R.string.description),
                                overflow = TextOverflow.Ellipsis,
                                fontFamily = fonts,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(0.dp, 2.dp, 0.dp, 10.dp)
                            )
                            Text(
                                text = description,
                                overflow = TextOverflow.Ellipsis,
                                fontFamily = fonts,
                                fontSize = 13.sp,
                                modifier = Modifier
                                    .padding(0.dp, 2.dp, 0.dp, 10.dp)
                            )
                        }
                    }
                }
            }
            // Button to toggle the content, centered at the bottom
            Button(
                onClick = { isButtonClicked = !isButtonClicked },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(if (isButtonClicked) purple100 else green87,
                    contentColor = Color.White
                )
            ) {
                Text(text = if (isButtonClicked) "Show Content" else "Show Location List")
            }
        }
    }
}

@Composable
fun LocationListRecommendation(
    locations: List<PlaceResult>,
    viewModel: ExplorerViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current)),
    navigateToDetailExplorerScreen: (String) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(130.dp),
        contentPadding = PaddingValues(1.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = rememberLazyGridState()
    ) {
        items(locations) {location ->
            DestinationItem(location = location, modifier = Modifier.clickable {
                viewModel.getLocationDetails(location.placeId)
                navigateToDetailExplorerScreen(location.placeId)
            })
        }
    }
}


fun convertToLowerCaseNoSpace(input: String): String {
    return input.toLowerCase().replace(" ", "")
}