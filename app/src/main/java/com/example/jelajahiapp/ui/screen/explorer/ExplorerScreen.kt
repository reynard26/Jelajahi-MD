//package com.example.jelajahiapp.ui.screen.explorer
//
//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.animation.core.tween
//import androidx.compose.animation.fadeIn
//import androidx.compose.animation.fadeOut
//import androidx.compose.animation.slideInVertically
//import androidx.compose.animation.slideOutVertically
//import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.lazy.grid.rememberLazyGridState
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.derivedStateOf
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.platform.testTag
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import kotlinx.coroutines.launch
//
//@Composable
//fun ExplorerScreen(
//    modifier: Modifier = Modifier,
////    viewModel: HomeViewModel = viewModel(
////        factory = ViewModelFactory.getInstance(LocalContext.current)
////    ),
//    navigateToDetail: (Long) -> Unit,
//) {
////    viewModel.uiState.collectAsState(initial = Result.Loading).value.let { uiState ->
////        when (uiState) {
////            is Result.Loading -> {
////                viewModel.getDogs()
////            }
////
////            is Result.Success -> {
////                HomeContent(
////                    listDog = uiState.data,
////                    navigateToDetail = navigateToDetail,
////                    viewModel = viewModel,
////                    modifier = modifier
////                )
////            }
////
////            is Result.Error -> {}
////        }
////    }
//}
//
//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun ExplorerContent(
////    listDog: List<Dog>,
//    navigateToDetail: (Long) -> Unit,
////    viewModel: HomeViewModel,
//    modifier: Modifier
//) {
//    val scope = rememberCoroutineScope()
//    val listState = rememberLazyGridState()
//    val query by viewModel.query.collectAsState()
//
//    val showButton: Boolean by remember {
//        derivedStateOf { listState.firstVisibleItemIndex > 0 }
//    }
//    Box(modifier = modifier) {
//        Column {
//            Text(
//                text = "Explorer",
//                overflow = TextOverflow.Ellipsis,
//                fontWeight = FontWeight.ExtraBold,
//                fontSize = 17.sp
//            )
//            Text(
//                text = "Recommendation destination for you",
//                overflow = TextOverflow.Ellipsis,
//                fontWeight = FontWeight.Thin,
//                fontSize = 13.sp
//            )
//
//            SearchBar(
//                query = query,
//                onQueryChange = { query ->
//                    viewModel.search(query)
//
//                    if (query.isEmpty()) {
//                        scope.launch {
//                            listState.animateScrollToItem(0)
//                        }
//                    }
//                }
//            )
//            LazyVerticalGrid(
//                columns = GridCells.Adaptive(160.dp),
//                contentPadding = PaddingValues(16.dp),
//                horizontalArrangement = Arrangement.spacedBy(16.dp),
//                verticalArrangement = Arrangement.spacedBy(16.dp),
//                modifier = modifier.testTag("DogList")
//            ) {
//                items(listDog) { itemsDog ->
//                    DogItems(
//                        dogName = itemsDog.dogName,
//                        image = itemsDog.image,
//                        country = itemsDog.country,
//                        modifier = Modifier
//                            .clickable {
//                                navigateToDetail(itemsDog.id)
//                            }
//                            .animateItemPlacement(tween(durationMillis = 500))
//                    )
//                }
//            }
//
//            AnimatedVisibility(
//                visible = showButton,
//                enter = fadeIn() + slideInVertically(),
//                exit = fadeOut() + slideOutVertically(),
//                modifier = Modifier
//                    .padding(bottom = 30.dp)
//            ) {
//                ScrollToTopButton(
//                    onClick = {
//                        scope.launch {
//                            listState.animateScrollToItem(index = 0)
//                        }
//                    }
//                )
//            }
//        }
//    }
//}