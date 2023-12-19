package com.example.jelajahiapp.ui.screen.home

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import coil.compose.rememberImagePainter
import com.example.jelajahiapp.R
import com.example.jelajahiapp.data.location.PlaceResult
import com.example.jelajahiapp.ui.screen.explorer.buildPhotoUrl
import com.example.jelajahiapp.ui.theme.Shapes
import com.example.jelajahiapp.ui.theme.fonts
import com.example.jelajahiapp.ui.theme.purple100
import com.example.jelajahiapp.ui.theme.white30

@Composable
fun HomeDestinationItem(
    location: PlaceResult,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .width(165.dp)
            .height(210.dp)
            .clip(shape = Shapes.large)
            .background(Color.Transparent)
    ) {
        location.photos?.firstOrNull()?.let { photo ->
            Image(
                painter = rememberImagePainter(data = buildPhotoUrl(photo.photoReference, 400)),
                contentDescription = null,
                contentScale = ContentScale.Crop, // Adjust as needed
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        Box(modifier = modifier
            .absolutePadding(8.dp, 120.dp, 0.dp, 0.dp)
            .width(150.dp)
            .height(80.dp)
            .clip(shape = Shapes.large)
            .background(white30)
        ){
            Column (modifier.padding(7.dp)){
                Text(
                    text = location.name.truncate(14),
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = fonts,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 13.sp,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row (modifier){
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = null,
                        tint = purple100,
                        modifier = modifier
                            .size(15.dp)
                    )

                    Text(
                        text = (location.vicinity).truncate(17),
                        fontFamily = fonts,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray,
                        fontSize = 10.sp,
                        )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Row (modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween){

                    location?.geometry?.location?.let { location ->
                        val geoUri = "${location.lat},${location.lng}"
                        Button(
                            onClick = {
                                val uri = "http://maps.google.com/maps?q=${geoUri}"
                                val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                                context.startActivity(mapIntent)
                            },
                            modifier = Modifier
                                .width(140.dp) // Increase the width to make the button larger
                                .height(26.dp),
                            colors = ButtonDefaults.buttonColors(purple100)
                        ) {
                            Text(
                                text = stringResource(id = R.string.route),
                                fontFamily = fonts,
                                fontWeight = FontWeight.Medium,
                                color = Color.White,
                                fontSize = 10.sp // Increase the font size

                            )
                        }
                    }
                }

        }

        }
    }
}


//Column(
//        modifier = modifier
//            .border(BorderStroke(1.dp, color = green87), shape = Shapes.large)
//            .width(170.dp)
//            .padding(8.dp), // Ensure the Column takes the full width
//        horizontalAlignment = Alignment.Start
//    ) {
//        Image(
//            painter = painterResource(image),
//            contentDescription = placeName,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .padding(5.dp, 10.dp, 5.dp, 7.dp)
//                .width(155.dp)
//                .height(120.dp)
//                .clip(Shapes.large)
//        )
//
//        Text(
//            text = placeName,
//            overflow = TextOverflow.Ellipsis,
//            fontWeight = FontWeight.ExtraBold,
//            fontSize = 17.sp,
//            modifier = Modifier
//                .padding(5.dp, 0.dp, 5.dp, 0.dp)
//        )
//        Row (modifier = Modifier
//            .padding(2.dp,2.dp, 2.dp, 10.dp)){
//            Icon(
//                imageVector = Icons.Default.Place,
//                contentDescription = null,
//                tint = purple100,
//                modifier = modifier
//                    .size(20.dp)
//            )
//
//            Text(
//                text = address,
//                overflow = TextOverflow.Ellipsis,
//                fontWeight = FontWeight.Bold,
//                color = Color.Gray
//            )
//        }
//    }
//}
