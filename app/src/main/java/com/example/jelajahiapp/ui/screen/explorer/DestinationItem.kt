package com.example.jelajahiapp.ui.screen.explorer

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jelajahiapp.R
import com.example.jelajahiapp.ui.theme.JelajahiAppTheme
import com.example.jelajahiapp.ui.theme.Shapes
import com.example.jelajahiapp.ui.theme.green87
import com.example.jelajahiapp.ui.theme.purple100

@Composable
fun DestinationItem(
    placeName: String,
    image: Int,
    address: String,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .border(BorderStroke(1.dp, color = green87), shape = Shapes.large)
            .width(170.dp)
            .padding(8.dp), // Ensure the Column takes the full width
        horizontalAlignment = Alignment.Start
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = placeName,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(5.dp, 10.dp, 5.dp, 7.dp)
                .width(155.dp)
                .height(120.dp)
                .clip(Shapes.large)
        )

        Text(
            text = placeName,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 17.sp,
            modifier = Modifier
                .padding(5.dp, 0.dp, 5.dp, 0.dp)
        )
        Row (modifier = Modifier
            .padding(2.dp,2.dp, 2.dp, 10.dp)){
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = null,
                tint = purple100,
                modifier = modifier
                    .size(20.dp)
            )

            Text(
                text = address,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemPreview() {
    JelajahiAppTheme {
        DestinationItem(
            placeName = "Golden Retriever",
            image = R.drawable.foto1,
            address = "Inggris"
        )
    }
}