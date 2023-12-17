package com.example.jelajahiapp.ui.screen.explorer

import android.util.Log
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jelajahiapp.data.ViewModelFactory
import com.example.jelajahiapp.data.location.PlaceResult
import com.example.jelajahiapp.data.retrofit.ExploreRequest
import com.example.jelajahiapp.ui.screen.authorization.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun ExplorerScreen(
    modifier: Modifier = Modifier,
    viewModel: ExplorerViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current))
){
    // Observe the responseLocation LiveData
    val responseLocation by viewModel.responseLocation.observeAsState()

    // Trigger API call
    DisposableEffect(Unit) {
        viewModel.getExplore("batiklasem")
        Log.d("resultssss",viewModel.getExplore("batiklasem").toString() )// Provide the desired query
        onDispose { }
    }

    // Display the result when available
    responseLocation?.let { response ->
        when {
            response.isSuccessful -> {
                // Use the LocationList Composable to display the list of locations
                LocationList(locations = response.body()?.results ?: emptyList())
            }
            else -> {
                // Handle error response if needed
                // You can display an error message or handle it in another way
            }
        }
    }
}

@Composable
fun LocationList(locations: List<PlaceResult>) {
    LazyColumn {
        items(locations) { location ->
            LocationItem(location = location)
        }
    }
}

@Composable
fun LocationItem(location: PlaceResult) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = location.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(text = "Rating: ${location.rating}")
    }
}