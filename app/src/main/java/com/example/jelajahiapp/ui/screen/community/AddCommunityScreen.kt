package com.example.jelajahiapp.ui.screen.community

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jelajahiapp.R
import com.example.jelajahiapp.ui.theme.JelajahiAppTheme
import com.example.jelajahiapp.ui.theme.fonts
import com.example.jelajahiapp.ui.theme.green40
import com.example.jelajahiapp.ui.theme.green87
import com.example.jelajahiapp.ui.theme.white100

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCommunityScreen(
    modifier: Modifier = Modifier,
    onCommunitySubmited: (placeName: String, location: String, description: String) -> Unit,
) {
    val placeNameState = remember { dataCommunityState() }
    val locationState = remember { dataCommunityState() }
    val descriptionState = remember { dataCommunityState() }

    Box(
        modifier = Modifier
            .padding(14.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            CommunityTextField(textFieldStateCommunity = placeNameState , label ="Place Name")
            Spacer(modifier = Modifier.height(8.dp))
            CommunityTextField(textFieldStateCommunity = locationState , label ="Location")
            Spacer(modifier = Modifier.height(8.dp))
            CommunityTextField(textFieldStateCommunity = descriptionState , label ="Description")

            val onSubmit = {
                if (placeNameState.isValid && locationState.isValid && descriptionState.isValid) {
                    onCommunitySubmited(placeNameState.text, locationState.text,descriptionState.text )
                }
            }
            Button(
                onClick = { onSubmit() },
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = green40,
                    disabledContentColor = white100,
                    contentColor = white100,
                    containerColor = green87// Set the color for the disabled state
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                enabled = placeNameState.isValid && locationState.isValid && descriptionState.isValid
            ) {
                Text(
                    color = white100,
                    fontFamily = fonts,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    text = stringResource(id = R.string.login)
                )
            }
        }
    }
}

@Preview(name = "Sign in light theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Sign in dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AddCommunityPreview() {
    JelajahiAppTheme {
        AddCommunityScreen(
            onCommunitySubmited = { _, _, _ -> },
        )
    }
}