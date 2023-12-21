package com.example.jelajahiapp.ui.screen.cultural

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.jelajahiapp.R
import com.example.jelajahiapp.component.ScrollToTopButton
import com.example.jelajahiapp.data.Result
import com.example.jelajahiapp.data.ViewModelFactory
import com.example.jelajahiapp.data.room.Cultural
import com.example.jelajahiapp.ui.screen.home.HomeViewModel
import com.example.jelajahiapp.ui.screen.home.truncate
import com.example.jelajahiapp.ui.theme.fonts
import com.example.jelajahiapp.ui.theme.purple100
import kotlinx.coroutines.launch

@Composable
fun CulturalScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    navigateToDetail: (Long) -> Unit,
    navigateBack: () -> Unit,
){
    viewModel.uiState.collectAsState(initial = Result.Loading).value.let { uiState ->
        when (uiState) {
            is Result.Loading -> {
                viewModel.getAllCultural()
            }

            is Result.Success -> {
                CulturalContent(
                    listCultural = uiState.data,
                    navigateToDetail = navigateToDetail,
                    modifier = modifier,
                    onBackClick = navigateBack,
                )
            }

            is Result.Error -> {}
        }
    }

}


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CulturalContent(
    listCultural: List<Cultural>,
    navigateToDetail: (Long) -> Unit,
    modifier: Modifier,
    onBackClick: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    val showButton: Boolean by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }
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
                title = {},
                actions = {
                    Image(painter = painterResource(id = R.drawable.logo),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(0.dp, 25.dp, 0.dp, 0.dp)
                    )
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = modifier.padding(innerPadding).padding(15.dp)) {
            Column {
                Text(
                    text = stringResource(id = R.string.cultural),
                    fontFamily = fonts,
                    fontWeight = FontWeight.ExtraBold,
                    color = purple100,
                    fontSize = 30.sp,
                    lineHeight = 30.sp
                )
                Text(
                    text = stringResource(id = R.string.explorer_indonesian),
                    fontFamily = fonts,
                    color = Color.Black,
                    fontSize = 15.sp,
                )
                Spacer(modifier = Modifier.height(10.dp))
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(130.dp),
                    contentPadding = PaddingValues(1.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = modifier.testTag("CulturalList")
                ) {
                    items(listCultural) { culturalItem ->
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

                AnimatedVisibility(
                    visible = showButton,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically(),
                    modifier = Modifier
                        .padding(bottom = 30.dp)
                ) {
                    ScrollToTopButton(
                        onClick = {
                            scope.launch {
                                listState.animateScrollToItem(index = 0)
                            }
                        }
                    )
                }
            }
        }
    }
}