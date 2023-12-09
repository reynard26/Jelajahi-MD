package com.example.jelajahiapp.ui.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jelajahiapp.R
import com.example.jelajahiapp.ui.screen.community.CommunityItem
import com.example.jelajahiapp.ui.theme.JelajahiAppTheme
import com.example.jelajahiapp.ui.theme.Shapes
import com.example.jelajahiapp.ui.theme.fonts
import com.example.jelajahiapp.ui.theme.green87
import com.example.jelajahiapp.ui.theme.grey40
import com.example.jelajahiapp.ui.theme.purple100
import com.example.jelajahiapp.ui.theme.white100

@Composable
fun OnBoarding(
    imagePlace: Int,
    heading: String,
    title: String,
    description: String,
    modifier: Modifier = Modifier,
){

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = heading,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 40.sp,
                color = purple100
            )

            Image(
                painter = painterResource(imagePlace),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )

            Spacer(modifier = Modifier.height(1.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding()
                .background(color = green87,
                    shape = RoundedCornerShape(
                        topStart = Shapes.extraLarge.topStart,
                        topEnd = Shapes.extraLarge.topEnd,
                        bottomEnd = CornerSize(0.dp),
                        bottomStart = CornerSize(0.dp)
                    )
                )
        ) {
            Column(modifier
                .padding(20.dp)
            ) {
                Text(
                    text = title,
                    letterSpacing = 2.sp,
                    lineHeight = 30.sp,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 35.sp,
                    color = white100
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = description,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    color = purple100
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun onBoardingPreview(){
    JelajahiAppTheme {
        OnBoarding(
            imagePlace = R.drawable.onboard_1,
            heading = "#Culture ",
            title = "Uncover Indonesia's Hidden Treasures",
            description = "Snap, discover, and explore Indonesia's culture with JelajahI's image recognition. Get instant mini-dictionary insights on cultural items.")
    }
}