package com.example.jelajahiapp.ui.screen.community

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.jelajahiapp.R
import com.example.jelajahiapp.component.BottomBar
import com.example.jelajahiapp.data.ViewModelFactory
import com.example.jelajahiapp.data.response.ResponseCommunity
import com.example.jelajahiapp.navigation.Screen
import com.example.jelajahiapp.ui.screen.community.viewmodel.CommunityViewModel
import com.example.jelajahiapp.ui.theme.fonts
import com.example.jelajahiapp.ui.theme.purple100

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityScreen(
    navController: NavHostController,
    viewModel: CommunityViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current))
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    DisposableEffect(Unit) {
        viewModel.getCommunity()
        onDispose { }
    }

    val allpostCommunity = viewModel.communityList.collectAsState().value

    // Observe the selected community post
    val selectedCommunity by viewModel.selectedCommunity.collectAsState()

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
                    text = stringResource(id = R.string.jelajahi_community),
                    fontFamily = fonts,
                    fontWeight = FontWeight.ExtraBold,
                    color = purple100,
                    fontSize = 30.sp,
                )
                Text(
                    text = stringResource(id = R.string.share_connect),
                    fontFamily = fonts,
                    color = Color.Black,
                    fontSize = 15.sp,
                )
                Spacer(modifier = Modifier.height(10.dp))
                if (viewModel.isLoading.value) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                CommunityList(postCommunity = allpostCommunity, viewModel = viewModel)
                Spacer(modifier = Modifier.height(10.dp))
            }
            FloatingActionButton(
                onClick = {  navController.navigate(Screen.AddCommunity.route) },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(0.dp,16.dp,8.dp,80.dp)
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
//            selectedCommunity?.let { community ->
//                val communityJson = Gson().toJson(community)
//                val encodedCommunity = Uri.encode(communityJson)
//                navController.navigate("${Screen.EditCommunity.route}/$encodedCommunity")
//            }
        }
    }
}


@Composable
fun CommunityList(
    postCommunity: List<ResponseCommunity>,
    viewModel: CommunityViewModel
) {
    LazyColumn(
        contentPadding = PaddingValues(1.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = rememberLazyListState()
    ) {
        items(postCommunity) { post ->
            CommunityItem(
                postCommunity = post,
                modifier = Modifier.clickable {
//                    viewModel.selectCommunityForEdit(post)
                }
            )
        }
    }
}