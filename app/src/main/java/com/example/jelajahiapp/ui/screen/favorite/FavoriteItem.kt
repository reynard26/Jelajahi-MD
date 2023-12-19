package com.example.jelajahiapp.ui.screen.favorite

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import com.example.jelajahiapp.data.location.Location
import com.example.jelajahiapp.data.location.Photo
import com.example.jelajahiapp.ui.screen.explorer.buildPhotoUrl
import com.example.jelajahiapp.ui.screen.home.truncate
import com.example.jelajahiapp.ui.theme.Shapes
import com.example.jelajahiapp.ui.theme.fonts
import com.example.jelajahiapp.ui.theme.green87
import com.example.jelajahiapp.ui.theme.grey40
import com.example.jelajahiapp.ui.theme.purple100


@Composable
fun FavoriteItem(
    photos: List<Photo>?,
    name: String,
    vicinity: String,
    geometry: Location,
    modifier: Modifier = Modifier,
){
    val firstPhoto = photos?.firstOrNull()
    val context = LocalContext.current
    Row(
        modifier = modifier
            .border(BorderStroke(1.dp, color = green87), shape = Shapes.large)
            .fillMaxWidth()
            .padding(10.dp)
            .heightIn(max = 80.dp)
            .clip(Shapes.medium)
        ,
    ){
        firstPhoto?.let {
            val imageUrl = buildPhotoUrl(it.photoReference, maxWidth = 200) // Set your desired maxWidth
            Image(
                painter = rememberImagePainter(data = imageUrl),
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(75.dp)
                    .height(75.dp)
                    .clip(Shapes.medium)
            )
        }

        Spacer(modifier = Modifier.width(4.dp))
        Column(
            modifier=modifier
                .fillMaxWidth(0.7f)){
            Text(
                text = name,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(3.dp, 0.dp, 3.dp, 0.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row {
                Icon(
                    imageVector = Icons.Default.Place,
                    contentDescription = null,
                    tint = grey40,
                    modifier = modifier
                        .size(20.dp)
                )

                Text(
                    text = vicinity.truncate(40),
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = grey40
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
        }

        geometry.location?.let { location ->
            val geoUri = "${location.lat},${location.lng}"
            Button(
                onClick = {
                    val uri = "http://maps.google.com/maps?q=${geoUri}"
                    val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                    context.startActivity(mapIntent)
                },
                modifier = modifier
                    .fillMaxWidth()
                    .absolutePadding(0.dp, 12.dp, 0.dp, 0.dp), colors = ButtonDefaults.buttonColors(purple100)
            ) {
                Text(
                    text = stringResource(id = R.string.route),
                    fontFamily = fonts,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    fontSize = 10.sp

                )
            }
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun ItemPreview(){
//    JelajahiAppTheme {
//        FavoriteItem(
//            photos = R.drawable.foto1,
//            name = "Toko Pakaian Batik",
//            vicinity = "Banyumass, Jawa Tengah"
//        )
//    }
//}