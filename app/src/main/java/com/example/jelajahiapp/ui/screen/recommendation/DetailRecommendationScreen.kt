package com.example.jelajahiapp.ui.screen.recommendation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jelajahiapp.R
import com.example.jelajahiapp.ui.theme.JelajahiAppTheme
import com.example.jelajahiapp.ui.theme.Shapes
import com.example.jelajahiapp.ui.theme.fonts
import com.example.jelajahiapp.ui.theme.green87
import com.example.jelajahiapp.ui.theme.grey40
import com.example.jelajahiapp.ui.theme.purple100

@Composable
fun DetailRecommendationContent(
    imagePlace: Int,
    placeName: String,
    nameUser: String,
    description: String,
    modifier: Modifier = Modifier,
){
    LazyColumn(
        modifier = modifier
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        item {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .heightIn(max = 130.dp)
                    .clip(Shapes.medium),
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth(0.65f)
                        .padding(2.dp)
                ) {

                    Text(
                        text = placeName,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )
                    Spacer(modifier = Modifier.height(1.dp))
                    Text(
                        text = nameUser,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = purple100
                    )
                    Spacer(modifier = Modifier.height(2.dp))
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
                        .width(130.dp)
                        .height(110.dp)
                        .clip(Shapes.medium)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(stringResource(id = R.string.destination_recommendation), fontFamily = fonts, fontWeight = FontWeight.Bold, color = green87, fontSize = 22.sp)
            LazyColumn(
                modifier = modifier.testTag("CulturalList")
            ) {
//                items(listCultural) { culturalItem ->
//                    DestinationItem(
//                        culturalName = culturalItem.culturalName,
//                        image = culturalItem.image,
//                        location = culturalItem.location.truncate(14),
//                        modifier = Modifier
//                            .clickable {
//                                navigateToDetail(culturalItem.id)
//                            }
//                            .animateItemPlacement(tween(durationMillis = 500))
//                    )
//                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ItemPreview(){
    JelajahiAppTheme {
        DetailRecommendationContent(
            imagePlace = R.drawable.foto1,
            placeName = "Batik Rakyat ",
            nameUser = "Issabelle Duchman",
            description = "lorem ipsumk sakdasnd ajsndjan sdjans jnasdjn asjdna jdna sjndajsnd ajsnd ajsnd jasndjasndj asn sasd asd adasdadas dasadas sdadas dada dadsas dadas a dsa sda da sda sd asd asd asd a da ssda sda da sda sd asd asd as das sdas das ")
    }
}