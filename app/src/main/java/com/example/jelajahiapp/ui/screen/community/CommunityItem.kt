package com.example.jelajahiapp.ui.screen.community

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jelajahiapp.R
import com.example.jelajahiapp.data.response.ResponseCommunity
import com.example.jelajahiapp.ui.theme.Shapes
import com.example.jelajahiapp.ui.theme.green87
import com.example.jelajahiapp.ui.theme.grey40
import com.example.jelajahiapp.ui.theme.purple100

@Composable
fun CommunityItem(
    postCommunity: ResponseCommunity,
    modifier: Modifier = Modifier,
){
    Row(
        modifier = modifier
            .border(BorderStroke(1.dp, color = green87), shape = Shapes.large)
            .fillMaxWidth()
            .padding(10.dp)
            .heightIn(max = 130.dp)
            .clip(Shapes.medium)
        ,
    ){
        Column(
            modifier=modifier
            .fillMaxWidth(0.65f)
                .padding(2.dp)){

            Text(
                text = postCommunity.placeName?: "",
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = postCommunity.location?: "",
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = purple100
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = postCommunity.description?: "",
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp,
                color = grey40
            )
        }
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = postCommunity.placeName?: "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(130.dp)
                .height(110.dp)
                .clip(Shapes.medium))
    }
}
//@Preview(showBackground = true)
//@Composable
//fun ItemPreview(){
//    JelajahiAppTheme {
//        CommunityItem(
//            imagePlace = R.drawable.foto1,
//            placeName = "Batik Rakyat ",
//            location = "Issabelle Duchman",
//            description = "lorem ipsumk sakdasnd ajsndjan sdjans jnasdjn asjdna jdna sjndajsnd ajsnd ajsnd jasndjasndj asn sasd asd adasdadas dasadas sdadas dada dadsas dadas a dsa sda da sda sd asd asd asd a da ssda sda da sda sd asd asd as das sdas das ")
//    }
//}