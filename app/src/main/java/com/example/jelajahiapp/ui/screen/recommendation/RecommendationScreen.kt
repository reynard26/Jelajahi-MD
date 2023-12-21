package com.example.jelajahiapp.ui.screen.recommendation

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jelajahiapp.R
import com.example.jelajahiapp.component.BottomBar
import com.example.jelajahiapp.data.ViewModelFactory
import com.example.jelajahiapp.navigation.Screen
import com.example.jelajahiapp.ui.screen.community.reduceFileImage
import com.example.jelajahiapp.ui.screen.community.uriToFile
import com.example.jelajahiapp.ui.theme.Shapes
import com.example.jelajahiapp.ui.theme.fonts
import com.example.jelajahiapp.ui.theme.green87
import com.example.jelajahiapp.ui.theme.purple100
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RecommendationScreen(
    navController: NavHostController
) {
    val cameraPermissionState: PermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    if (cameraPermissionState.status.isGranted) {
        CameraScreen(navController = navController)
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                Icon(imageVector = Icons.Default.Camera, contentDescription = "Camera")
                Text(text = "Grant permission")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraScreen(
    navController: NavHostController
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Detail.route) {
                BottomBar(navController)
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .padding(15.dp)
                .fillMaxWidth()
        ) {
            Column() {
                Text(
                    text = stringResource(id = R.string.culture_identification),
                    fontFamily = fonts,
                    fontWeight = FontWeight.ExtraBold,
                    color = purple100,
                    fontSize = 30.sp,
                )
                Text(
                    text = stringResource(id = R.string.capture_get),
                    fontFamily = fonts,
                    color = Color.Black,
                    fontSize = 15.sp,
                )

                ImagePicker(
                    onImageSelected = { uri ->
                        selectedImageUri = uri
                    }
                )
                selectedImageUri?.let { uri ->
                    // Automatically trigger UploadImageSection
                    UploadImageSection(
                        selectedImageUri = uri,
                        onUploadSuccess = {
                        },
                        onUploadError = {
                        }
                    )
                }
            }
        }
    }
}


class CameraImageContract : ActivityResultContract<Unit, Uri?>() {

    private var imageFileUri: Uri? = null

    override fun createIntent(context: Context, input: Unit): Intent {
        val imageFileName = "captured_image.jpg"
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.TITLE, imageFileName)
        }
        imageFileUri = context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        )
        return Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        return if (resultCode == Activity.RESULT_OK) {
            imageFileUri
        } else {
            null
        }
    }
}
@Composable
fun ImagePicker(
    onImageSelected: (Uri) -> Unit,
    modifier: Modifier = Modifier,
) {
    var hasImage by remember {
        mutableStateOf(false)
    }
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            hasImage = uri != null
            imageUri = uri
            if (uri != null) {
                onImageSelected(uri)
            }
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = CameraImageContract(),
        onResult = { uri ->
            hasImage = uri != null
            imageUri = uri
            if (uri != null) {
                onImageSelected(uri)
            }
        }
    )
    Column(
        modifier = Modifier
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(250.dp)
                .clip(Shapes.medium)
                .clickable {
                    imagePicker.launch("image/*")
                }
        ) {
            Icon(
                Icons.Default.PhotoLibrary, contentDescription = null,
                modifier = Modifier.fillMaxSize(), tint = green87
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        Box(
            modifier = Modifier
                .size(250.dp)
                .clip(Shapes.medium)
                .clickable {
                    cameraLauncher.launch(Unit)
                }
        ) {
            Icon(
                Icons.Default.CameraAlt, contentDescription = null,
                modifier = Modifier.fillMaxSize(), tint = purple100
            )
        }
    }
}




@Composable
fun UploadImageSection(
    selectedImageUri: Uri?,
    viewModel: RecommendationViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    onUploadSuccess: () -> Unit,
    onUploadError: () -> Unit
) {
    if (selectedImageUri == null) {
        Text(text = "Please select an image")
    } else {
        val imageFile = uriToFile(selectedImageUri, LocalContext.current)?.reduceFileImage()

        if (imageFile != null && imageFile.exists()) {
            val requestImageFile = imageFile.asRequestBody("image/*".toMediaType())
            val imageMultipart = MultipartBody.Part.createFormData("file", imageFile.name, requestImageFile)
            viewModel.recommendation(imageMultipart, onUploadSuccess, onUploadError)
        } else {
            onUploadError()
        }
    }
}