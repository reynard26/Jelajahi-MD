package com.example.jelajahiapp.ui.screen.community

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jelajahiapp.R
import com.example.jelajahiapp.data.response.ResponseCommunity
import com.example.jelajahiapp.ui.screen.home.truncate
import com.example.jelajahiapp.ui.theme.Shapes
import com.example.jelajahiapp.ui.theme.fonts
import com.example.jelajahiapp.ui.theme.purple100
import com.example.jelajahiapp.ui.theme.white100

@Composable
fun HomeCommunityItem(
    community: ResponseCommunity,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier
        .height(75.dp)
        .widthIn(max = 400.dp)
        .background(color = purple100, shape = Shapes.medium)){
        Row (modifier = modifier
            .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.icononly),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(3.dp, 0.dp, 3.dp, 0.dp)
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Column(
                modifier = modifier
                    .padding(3.dp),
                horizontalAlignment = Alignment.Start
            ){
                community.placeName?.let { Text(text = it, fontFamily = fonts, fontWeight = FontWeight.Bold, color = white100, fontSize = 14.sp) }
                Spacer(modifier = Modifier.height(5.dp))
                Box (modifier.background(color = white100, shape = Shapes.small).padding(3.dp)){
                    community.description?.let { Text(text = it.truncate(80), fontFamily = fonts, color = purple100, fontSize = 10.sp,lineHeight = 10.sp) }
                }
            }
        }
    }
}
