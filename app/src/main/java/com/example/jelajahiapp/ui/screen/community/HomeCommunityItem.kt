package com.example.jelajahiapp.ui.screen.community

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jelajahiapp.R
import com.example.jelajahiapp.ui.screen.home.MyFavoriteIcon
import com.example.jelajahiapp.ui.theme.JelajahiAppTheme
import com.example.jelajahiapp.ui.theme.Shapes
import com.example.jelajahiapp.ui.theme.fonts
import com.example.jelajahiapp.ui.theme.purple100
import com.example.jelajahiapp.ui.theme.white100
import com.example.jelajahiapp.ui.theme.white30

@Composable
fun HomeCommunityItem(
    placeName: String,
    image: Int,
    description: String,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier
        .background(color = purple100, shape = Shapes.medium)){
        Row (modifier = modifier
            .padding(2.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(3.dp, 0.dp, 3.dp, 0.dp)
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Column(
                modifier = modifier
                    .padding(3.dp), // Ensure the Column takes the full width
                horizontalAlignment = Alignment.Start
            ){
                Text(text = placeName, fontFamily = fonts, fontWeight = FontWeight.Bold, color = white100, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(5.dp))
                Box (modifier.background(color = white100, shape = Shapes.small).padding(3.dp)){
                    Text(text = description, fontFamily = fonts, color = purple100, fontSize = 10.sp,lineHeight = 10.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemPreviews() {
    JelajahiAppTheme {
        HomeCommunityItem(
            placeName = "Golden Retriever",
            image = R.drawable.lasem,
            description = "Capture your Indonesian cultural moments now, and let us suggest destinations personalized for you!"
        )
    }
}