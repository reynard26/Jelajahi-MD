package com.example.jelajahiapp.ui.screen.cultural

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
import com.example.jelajahiapp.ui.theme.purple100

@Composable
fun CulturalItem(
    itemName: String,
    image: Int,
    from: String,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .border(BorderStroke(2.dp, color = purple100), shape = Shapes.large)
            .width(180.dp)
            .padding(8.dp), // Ensure the Column takes the full width
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = itemName,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.ExtraBold,
            color = purple100,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(3.dp, 0.dp, 3.dp, 0.dp)
        )
        Row (modifier = Modifier
            .padding(2.dp,2.dp, 2.dp, 0.dp)){
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = null,
                tint = Color.Gray,
                modifier = modifier
                    .size(20.dp)
            )

            Text(
                text = from,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                fontSize = 15.sp
            )
        }
        Image(
            painter = painterResource(image),
            contentDescription = itemName,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(5.dp, 5.dp, 5.dp, 7.dp)
                .width(170.dp)
                .height(110.dp)
                .clip(Shapes.large)
        )

    }
}

@Preview(showBackground = true)
@Composable
fun ItemPreview() {
    JelajahiAppTheme {
        CulturalItem(
            itemName = "Golden Retriever",
            image = R.drawable.foto1,
            from = "Inggris"
        )
    }
}