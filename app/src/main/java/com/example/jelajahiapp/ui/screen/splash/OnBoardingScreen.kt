package com.example.jelajahiapp.ui.screen.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.jelajahiapp.navigation.Screen
import com.example.jelajahiapp.ui.theme.Shapes
import com.example.jelajahiapp.ui.theme.fonts
import com.example.jelajahiapp.ui.theme.green87
import com.example.jelajahiapp.ui.theme.purple100
import com.example.jelajahiapp.ui.theme.white10
import com.example.jelajahiapp.ui.theme.white100
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
@Composable
fun OnBoardingScreen(
    navController: NavHostController,
    onboardingViewModel: OnboardingViewmodel = hiltViewModel()
){
    val pages = listOf(
        DataOnBoarding.First,
        DataOnBoarding.Second,
        DataOnBoarding.Third
    )
    val pagerState = rememberPagerState()

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            modifier = Modifier.weight(10f),
            count = 3,
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { position ->
            ContentOnboarding(dataOnBoarding = pages[position])
        }

        Column(modifier = Modifier.fillMaxSize()
            .weight(1f)
            .background(color = green87)) {
            StartButton(
                modifier = Modifier.weight(1f),
                pagerState = pagerState
            ) {
                onboardingViewModel.saveOnBoardingState(completed = true)
                navController.popBackStack()
                navController.navigate(Screen.Login.route)
            }
            HorizontalPagerIndicator(
                activeColor = white100,
                inactiveColor = white10,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .weight(0.7f),
                pagerState = pagerState
            )
        }
    }
}
@Composable
fun ContentOnboarding(dataOnBoarding: DataOnBoarding,
                      modifier: Modifier = Modifier){
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
                text = dataOnBoarding.heading,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                fontFamily = fonts,
                fontSize = 40.sp,
                color = purple100
            )

            Image(
                painter = painterResource(id = dataOnBoarding.image),
                contentDescription = dataOnBoarding.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(400.dp)
            )

            Spacer(modifier = Modifier.height(1.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.2f)
                .padding()
                .background(
                    color = green87,
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
                    text = dataOnBoarding.title,
                    letterSpacing = 1.sp,
                    lineHeight = 30.sp,
                    fontFamily = fonts,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    color = white100
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = dataOnBoarding.description,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Medium,
                    fontSize = 19.sp,
                    fontFamily = fonts,
                    color = purple100
                )
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun StartButton(
    modifier: Modifier,
    pagerState: PagerState,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = pagerState.currentPage == 2
        ) {
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    contentColor = white100,
                    backgroundColor = purple100
                )
            ) {
                Text(
                    text = "Start",
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.sp,
                    fontFamily = fonts,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun FirstOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        ContentOnboarding(dataOnBoarding = DataOnBoarding.First)
    }
}

@Composable
@Preview(showBackground = true)
fun SecondOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        ContentOnboarding(dataOnBoarding = DataOnBoarding.Second)
    }
}

@Composable
@Preview(showBackground = true)
fun ThirdOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        ContentOnboarding(dataOnBoarding = DataOnBoarding.Third)
    }
}