package com.example.jelajahiapp.ui.screen.community

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import com.example.jelajahiapp.ui.theme.grey10
import com.example.jelajahiapp.ui.theme.grey40
import com.example.jelajahiapp.ui.theme.purple100

@Composable
fun CommunityItem(
    imagePlace: Int,
    placeName: String,
    nameUser: String,
    description: String,
    modifier: Modifier = Modifier,
){
    Row(
        modifier = modifier
            .border(BorderStroke(1.dp, color = green87), shape = Shapes.large)
            .fillMaxWidth(),
    ){
        Column(
            modifier=modifier
            .fillMaxWidth()){

            Text(
                text = placeName,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
            )

            Text(
                text = nameUser,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp,
                color = purple100
            )

            Text(
                text = description,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp,
                color = grey40
            )
        }
        Image(
            painter = painterResource(imagePlace),
            contentDescription = placeName,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(160.dp))
    }
}
@Preview(showBackground = true)
@Composable
fun ItemPreview(){
    JelajahiAppTheme {
        CommunityItem(
            imagePlace = R.drawable.foto1,
            placeName = "SOLO",
            nameUser = "BUDI",
            description = " lorem ipsumk sakdasnd ajsndjan sdjans jnasdjn asjdna jdna sjndajsnd ajsnd ajsnd jasndjasndj asn")
    }
}