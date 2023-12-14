package com.example.jelajahiapp.ui.screen.community

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.jelajahiapp.data.ViewModelFactory
import com.example.jelajahiapp.ui.screen.authorization.viewmodel.UserViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun AddCommunityScreen(
    navController: NavHostController,
    viewModel: UserViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current))
){

}

@Composable
fun AddCommunityContent(
    onCommunitySubmitted: (image: MultipartBody.Part, placeName: String, location: String, description: String) -> Unit,
) {
    val placeNameState = remember { dataCommunityState() }
    val locationState = remember { dataCommunityState() }
    val descriptionState = remember { dataCommunityState() }

    var capturedImage: Bitmap? by remember { mutableStateOf(null) }


    Box(
        modifier = Modifier
            .padding(14.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
//            CameraPreviewWithCaptureButton(onImageCaptured = { image ->
//                capturedImage = image
//            })

            // Display the captured image
            if (capturedImage != null) {
                Image(
                    bitmap = capturedImage!!.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(shape = RoundedCornerShape(8.dp))
                        .padding(vertical = 8.dp)
                )
            }

            CommunityTextField(textFieldStateCommunity = placeNameState , label ="Place Name")
            Spacer(modifier = Modifier.height(8.dp))
            CommunityTextField(textFieldStateCommunity = locationState , label ="Location")
            Spacer(modifier = Modifier.height(8.dp))
            CommunityTextField(textFieldStateCommunity = descriptionState , label ="Description")

            Button(
                onClick = {
                    if (placeNameState.isValid && locationState.isValid && descriptionState.isValid) {
                        // Check if an image is captured
                        val imagePart = capturedImage?.let { bitmap ->
                            val stream = ByteArrayOutputStream()
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                            val byteArray = stream.toByteArray()
                            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), byteArray)
                            MultipartBody.Part.createFormData("image", "image.png", requestFile)
                        }

                        onCommunitySubmitted(
                            imagePart!!,
                            placeNameState.text,
                            locationState.text,
                            descriptionState.text
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                enabled = placeNameState.isValid && locationState.isValid && descriptionState.isValid
            ) {
                Text(
                    text = "Submit",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
    }
}



//@Preview(name = "Sign in light theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
//@Preview(name = "Sign in dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//fun AddCommunityPreview() {
//    JelajahiAppTheme {
//        AddCommunityScreen(
//            onCommunitySubmited = { _, _, _ -> },
//        )
//    }
//}