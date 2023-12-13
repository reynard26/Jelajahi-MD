package com.example.jelajahiapp.ui.screen.cultural

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jelajahiapp.R
import com.example.jelajahiapp.data.Result
import com.example.jelajahiapp.data.ViewModelFactory
import com.example.jelajahiapp.ui.theme.JelajahiAppTheme
import com.example.jelajahiapp.ui.theme.Shapes
import com.example.jelajahiapp.ui.theme.fonts
import com.example.jelajahiapp.ui.theme.grey40
import com.example.jelajahiapp.ui.theme.purple100

@Composable
fun DetailCulturalScreen(
    culturalId: Long,
    viewModel: DetailCulturalViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    navigateBack: () -> Unit,
) {

    viewModel.uiState.collectAsState(initial =Result.Loading).value.let { uiState ->
        when (uiState) {
            is Result.Loading -> {
                viewModel.getCulturalById(culturalId)
            }

            is Result.Success -> {
                DetailContent(
                    image = uiState.data.image,
                    culturalName = uiState.data.culturalName,
                    culturalType = uiState.data.culturalType,
                    location = uiState.data.location,
                    description = uiState.data.description,
                    onBackClick = navigateBack,
                )
            }

            is Result.Error -> {}
            else -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    @DrawableRes image: Int,
    culturalName: String,
    culturalType: String,
    location: String,
    description: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
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
        Box(modifier = modifier
            .padding(innerPadding)) {
            Image(
                painter = painterResource(image),
                contentDescription = culturalName,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .height(350.dp)
                    .fillMaxWidth()
            )
            Box(modifier = modifier
                .absolutePadding(0.dp, 250.dp, 0.dp, 0.dp)
                .padding(15.dp)
                .background(color = Color.White, Shapes.large)
            ){

                LazyColumn(
                    modifier = modifier
                        .padding(20.dp)
                        .fillMaxWidth()
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
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview2() {
    JelajahiAppTheme {
        DetailContent(R.drawable.truntum,
            "Golden Retriever",
            "Batik",
            "Jawa Timur",
            "Golden Retriever merupakan anjing ras modern yang populer dijadikan peliharaaan keluarga. Bulu anjing ini berwarna keemasan (golden) di bawah sinar matahari sehingga disebut Golden Retriever. Golden Retriever dikenal sebagai anjing yang cerdas dan berani. Golden retriever juga dikenal memiliki penciuman yang tajam sehingga tak jarang dijadikan teman berburu.",
            onBackClick = { }
        )
    }
}