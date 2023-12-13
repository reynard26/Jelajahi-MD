package com.example.jelajahiapp.ui.screen.home

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
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
import com.example.jelajahiapp.data.room.Cultural
import com.example.jelajahiapp.navigation.Screen
import com.example.jelajahiapp.ui.screen.cultural.CulturalItem
import com.example.jelajahiapp.ui.theme.fonts
import com.example.jelajahiapp.ui.theme.purple100

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    navigateToDetail: (Long) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

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
                    IconButton(
                        onClick = {
                            // Navigate to the Register screen when the icon is clicked
//                            navController.navigate(Screen.Register.route)
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                    }
                }
            )
        }
    ) {
        LaunchedEffect(Unit) {
            viewModel.fetchToken()
        }
        val token by viewModel.tokenFlow.collectAsState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Spacer(modifier = Modifier.height(70.dp))
            Text(
                text = stringResource(id = R.string.home_text),
                fontFamily = fonts,
                fontWeight = FontWeight.ExtraBold,
                color = purple100,
                fontSize = 26.sp,
                lineHeight = 25.sp
            )

            viewModel.uiState.collectAsState(initial = Result.Loading).value.let { uiState ->
                when (uiState) {
                    is Result.Loading -> {
                        viewModel.getAllCultural()
                    }

                    is Result.Success -> {
                        HomeContent(
                            listCultural = uiState.data, // Access the data using getOrThrow()
                            navigateToDetail = navigateToDetail,
                            modifier = modifier
                        )
                    }

                    is Result.Error -> {
                        // Handle error case
                    }

                    else -> {}
                }
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
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    val showButton: Boolean by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }

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

fun String.truncate(maxCharacters: Int): String {
    return if (length > maxCharacters) {
        "${take(maxCharacters)}..."
    } else {
        this
    }
}