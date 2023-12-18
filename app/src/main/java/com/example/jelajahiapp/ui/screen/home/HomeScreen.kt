package com.example.jelajahiapp.ui.screen.home

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
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
import com.example.jelajahiapp.data.room.Cultural
import com.example.jelajahiapp.navigation.Screen
import com.example.jelajahiapp.ui.screen.community.HomeCommunityItem
import com.example.jelajahiapp.ui.screen.cultural.CulturalItem
import com.example.jelajahiapp.ui.screen.recommendation.RecommendationActivity
import com.example.jelajahiapp.ui.theme.Shapes
import com.example.jelajahiapp.ui.theme.fonts
import com.example.jelajahiapp.ui.theme.green87
import com.example.jelajahiapp.ui.theme.purple100
import com.example.jelajahiapp.ui.theme.white100

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    navigateToDetail: (Long) -> Unit,
    navigateToDetailExplorer: (String) -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val locations by viewModel.locations.collectAsState(emptyList())
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getRandomLocation()
    }

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Detail.route) {
                BottomBar(navController)
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Image(painter = painterResource(id = R.drawable.logo),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(0.dp, 25.dp, 0.dp, 0.dp)
                    )
                },
                actions = {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(Icons.Default.MoreVert, contentDescription = null)
                    }

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(onClick = {
                            viewModel.setLogout {
                                showMenu = false
                                navController.navigate(Screen.Login.route) {
                                    popUpTo(Screen.Login.route) {
                                        inclusive = true
                                    }
                                }
                            }
                        }) {
                            Text(text = "Logout")
                        }
                    }
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            item{Spacer(modifier = Modifier.height(70.dp))
                Text(
                    text = stringResource(id = R.string.home_text),
                    fontFamily = fonts,
                    fontWeight = FontWeight.ExtraBold,
                    color = purple100,
                    fontSize = 30.sp,
                    lineHeight = 30.sp
                )

                Box(modifier = modifier
                    .background(color = purple100, shape = Shapes.medium)
                    .fillMaxWidth()){
                    Column (modifier = modifier
                        .padding(10.dp)) {
                        Text(text = stringResource(id = R.string.travel_references), fontFamily = fonts, fontWeight = FontWeight.Bold, color = white100, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(text = stringResource(id = R.string.capture_your), fontFamily = fonts, color = white100, fontSize = 12.sp)
                        Button(onClick = { context.startActivity(Intent(context, RecommendationActivity::class.java)) },
                            colors = ButtonDefaults.buttonColors(Color.White)) {
                            Text(text = stringResource(id = R.string.capture_image), color = purple100)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.popular),
                        fontFamily = fonts,
                        fontWeight = FontWeight.Bold,
                        color = green87,
                        fontSize = 22.sp
                    )
                    Text(
                        text = stringResource(id = R.string.see_all),
                        fontFamily = fonts,
                        color = purple100,
                        fontSize = 13.sp,
                        modifier = Modifier
                            .padding(0.dp, 5.dp, 0.dp, 0.dp)
                            .clickable {
                                navController.navigate(route = Screen.Explorer.route) {
                                    launchSingleTop = true
                                    popUpTo(Screen.Home.route) {
                                        inclusive = true
                                    }
                                }
                            }
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                HomeDestinationContent(
                    locations = locations,
                    navigateToDetailExplorer = navigateToDetailExplorer,
                    modifier = modifier
                )
                viewModel.uiState.collectAsState(initial = Result.Loading).value.let { uiState ->
                    when (uiState) {
                        is Result.Loading -> {
                            viewModel.getAllCultural()
                        }

                        is Result.Success -> {
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(modifier = modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(stringResource(id = R.string.cultural), fontFamily = fonts, fontWeight = FontWeight.Bold, color = green87, fontSize = 22.sp)
                                Text(stringResource(id = R.string.see_all), fontFamily = fonts, color = purple100, fontSize = 13.sp, modifier = Modifier
                                    .padding(0.dp, 5.dp, 0.dp, 0.dp)
                                    .clickable { navController.navigate(Screen.Cultural.route) })
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            HomeContent(
                                listCultural = uiState.data,
                                navigateToDetail = navigateToDetail,
                                modifier = modifier
                            )
                        }
                        else -> {}
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(stringResource(id = R.string.community_stories), fontFamily = fonts, fontWeight = FontWeight.Bold, color = green87, fontSize = 22.sp)
                    Text(stringResource(id = R.string.see_all), fontFamily = fonts, color = purple100, fontSize = 13.sp, modifier = Modifier
                        .padding(0.dp, 5.dp, 0.dp, 0.dp)
                        .clickable { navController.navigate(Screen.Cultural.route) })
                }
                Spacer(modifier = Modifier.height(10.dp))
                HomeCommunityContent(modifier = modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(70.dp))
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    listCultural: List<Cultural>,
    navigateToDetail: (Long) -> Unit,
    modifier: Modifier
) {
    val listState = rememberLazyListState()

    Box(modifier = modifier) {
        Row {
            LazyRow(
                state = listState,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = modifier
                    .testTag("CulturalList")
            ) {
                items(listCultural, key = { it.id }) { culturalItem ->
                    CulturalItem(
                        culturalName = culturalItem.culturalName,
                        image = culturalItem.image,
                        location = culturalItem.location.truncate(14),
                        modifier = Modifier
                            .clickable {
                                navigateToDetail(culturalItem.id)
                            }
                            .animateItemPlacement(tween(durationMillis = 500))
                    )
                }
            }
        }
    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeDestinationContent(
    locations: List<PlaceResult>,
    navigateToDetailExplorer: (String) -> Unit,
    modifier: Modifier
) {
    val listState = rememberLazyListState()

    Box(modifier = modifier) {
        LazyRow(
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(locations) { location ->
                HomeDestinationItem(location = location, modifier = modifier.clickable {
                    navigateToDetailExplorer(location.placeId)
                }.animateItemPlacement(tween(durationMillis = 500)))
            }
        }
    }
}



@Composable
fun HomeCommunityContent(
    modifier: Modifier
) {
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    Box(modifier = modifier) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .testTag("CulturalList")
            ) {
                    HomeCommunityItem(
                        placeName = "Batik Rakyat Magelang",
                        image = R.drawable.lasem,
                        description = stringResource(R.string.capture_your).truncate(230),
                        modifier = Modifier
                    )
            }
    }
}

fun String.truncate(maxCharacters: Int): String {
    return if (length > maxCharacters) {
        "${take(maxCharacters)}..."
    } else {
        this
    }
}
