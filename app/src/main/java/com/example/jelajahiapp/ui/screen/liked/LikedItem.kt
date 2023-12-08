package com.example.jelajahiapp.ui.screen.liked

import android.text.Layout.Alignment
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.VerticalAlignmentLine
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
import com.example.jelajahiapp.ui.theme.grey40
import com.example.jelajahiapp.ui.theme.purple100
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@Composable
fun LikedItem(
    imagePlace: Int,
    placeName: String,
    nameUser: String,
    description: String,
    modifier: Modifier = Modifier,
){
    Row(
        modifier = modifier
            .border(BorderStroke(1.dp, color = green87), shape = Shapes.large)
            .fillMaxWidth()
            .padding(10.dp)
            .heightIn(max = 80.dp)
            .clip(Shapes.medium)
        ,
    ){
        Image(
            painter = painterResource(imagePlace),
            contentDescription = placeName,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(75.dp)
                .height(75.dp)
                .clip(Shapes.medium))

        Spacer(modifier = Modifier.width(4.dp))
        Column(
            modifier=modifier
                .fillMaxWidth(0.7f)){
            Text(
                text = placeName,
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
                    text = nameUser,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = grey40
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = description,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp,
                color = grey40,
                modifier = Modifier
                    .padding(3.dp, 0.dp, 3.dp, 0.dp)
            )
        }
        Button(
            onClick = { /*TODO*/ },
            modifier = modifier
                .fillMaxWidth()
                .absolutePadding(0.dp, 12.dp, 0.dp, 0.dp), colors = ButtonDefaults.buttonColors(purple100)
        ){
            Text(text = "Route")
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ItemPreview(){
    JelajahiAppTheme {
        LikedItem(
            imagePlace = R.drawable.foto1,
            placeName = "Toko Pakaian Batik",
            nameUser = "Banyumass, Jawa Tengah",
            description = "lorem ipsumk sakdasnd ajsndjan sdjans jnasdjn asjdna jdna sjndajsnd ajsnd ajsnd jasndjasndj asn sasd asd adasdadas dasadas sdadas dada dadsas dadas a dsa sda da sda sd asd asd asd a da ssda sda da sda sd asd asd as das sdas das ")
    }
}