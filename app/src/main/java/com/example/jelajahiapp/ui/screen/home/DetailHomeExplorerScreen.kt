package com.example.jelajahiapp.ui.screen.home

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.jelajahiapp.R
import com.example.jelajahiapp.data.ViewModelFactory
import com.example.jelajahiapp.data.location.PlaceResult
import com.example.jelajahiapp.ui.screen.explorer.buildPhotoUrl
import com.example.jelajahiapp.ui.theme.Shapes
import com.example.jelajahiapp.ui.theme.fonts
import com.example.jelajahiapp.ui.theme.green87
import com.example.jelajahiapp.ui.theme.grey40
import com.example.jelajahiapp.ui.theme.purple100

@Composable
fun DetailHomeExplorerScreen(
    placeId: String,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),

    navigateBack: () -> Unit,
) {
    viewModel.updateStatus(placeId)
    val favoriteStatus by viewModel.favoritePlaceStatus.collectAsState(initial = false)
    val isLoadingDetails by viewModel.isLoadingDetails.collectAsState()

    LaunchedEffect(placeId) {
        viewModel.showLocationDetails(placeId)
    }

    val locationDetails by viewModel.locationDetails.collectAsState()

    if (isLoadingDetails) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.Center)
        )
    } else {
        // Show details screen
        DetailHomeExplorerContent(
            locationDetails = locationDetails,
            onBackClick = navigateBack,
            favoritePlaceStatus = favoriteStatus,
            updateFavoriteStatus = { viewModel.changeFavorite(locationDetails!!) }
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailHomeExplorerContent(
    locationDetails: PlaceResult?,
    onBackClick: () -> Unit,
    favoritePlaceStatus: Boolean,
    updateFavoriteStatus: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { onBackClick() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                title = {
                    locationDetails?.let {
                        Text(
                            text = it.name,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        locationDetails?.let {
            Log.d("MANADATANYA", locationDetails.toString())
            Box(
                modifier = modifier
                    .padding(innerPadding)
            ) {
                locationDetails.photos?.firstOrNull()?.let { photo ->
                    Image(
                        painter = rememberImagePainter(data = buildPhotoUrl(photo.photoReference, 400)),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .height(350.dp)
                            .fillMaxWidth()
                            .clip(Shapes.medium)
                    )
                }
                IconButton(
                    onClick = updateFavoriteStatus,
                    modifier = modifier
                        .background(green87, shape = Shapes.medium)
                        .align(alignment = Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = if (favoritePlaceStatus) {
                            Icons.Default.Favorite
                        } else {
                            Icons.Default.FavoriteBorder
                        },
                        contentDescription = if (favoritePlaceStatus) {
                            stringResource(R.string.remove_favorite)
                        } else {
                            stringResource(R.string.add_favorite)
                        },

                        )
                }
                Box(
                    modifier = modifier
                        .absolutePadding(0.dp, 250.dp, 0.dp, 0.dp)
                        .padding(15.dp)
                        .background(color = Color.White, Shapes.large)
                        .border(BorderStroke(1.dp, color = green87), shape = Shapes.large)
                ) {
                    Column(
                        modifier = modifier
                            .padding(20.dp)
                            .fillMaxWidth()

                    ) {
                        Text(
                            text = locationDetails.name,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 25.sp,
                            fontFamily = fonts,
                            color = green87
                        )
                        fun String.capitalizeEachWord(): String {
                            return split(" ").joinToString(" ") { it.capitalize() }
                        }

                        val typesString: String = locationDetails.types.joinToString(", ") { type ->
                            type.replace('_', ' ')
                        }.capitalizeEachWord()

                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                                }
                                append(typesString)
                            },
                            fontSize = 13.sp,
                            color = grey40,
                        )

                        Spacer(modifier = Modifier.height(5.dp))
                        Row {
                            Text(
                                text = locationDetails.rating.toString(),
                                overflow = TextOverflow.Ellipsis,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                fontFamily = fonts,
                                color = grey40,
                                modifier = Modifier
                                    .padding(0.dp, 2.dp, 0.dp, 10.dp)

                            )
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = purple100,
                                modifier = modifier
                                    .size(18.dp)
                            )

                            Text(
                                text = stringResource(id = R.string.user_total_ratings,locationDetails.userRatingsTotal.toString()),
                                overflow = TextOverflow.Ellipsis,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                fontFamily = fonts,
                                color = grey40,
                                modifier = Modifier
                                    .padding(0.dp, 2.dp, 0.dp, 10.dp)

                            )
                        }
                        Row {
                            Icon(
                                imageVector = Icons.Default.Place,
                                contentDescription = null,
                                tint = grey40,
                                modifier = modifier
                                    .size(18.dp)
                            )

                            Text(
                                text = locationDetails.vicinity,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 12.sp,
                                fontFamily = fonts,
                                color = grey40,
                                modifier = Modifier
                                    .padding(0.dp, 2.dp, 0.dp, 10.dp)

                            )
                        }


                        locationDetails?.geometry?.location?.let { location ->
                            val geoUri = "${location.lat},${location.lng}"
                            Button(
                                onClick = {
                                    val uri = "http://maps.google.com/maps?q=${geoUri}"
                                    val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                                    context.startActivity(mapIntent)
                                },
                                modifier = modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(green87)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.route)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
