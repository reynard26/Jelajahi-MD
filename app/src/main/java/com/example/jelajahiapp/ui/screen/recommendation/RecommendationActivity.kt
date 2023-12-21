//package com.example.jelajahiapp.ui.screen.recommendation
//
//import android.Manifest
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.net.Uri
//import android.os.Build
//import android.os.Bundle
//import android.view.WindowInsets
//import android.view.WindowManager
//import android.widget.Toast
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.activity.viewModels
//import androidx.appcompat.app.AppCompatActivity
//import androidx.camera.core.CameraSelector
//import androidx.camera.core.ImageCapture
//import androidx.camera.core.ImageCaptureException
//import androidx.camera.core.Preview
//import androidx.camera.lifecycle.ProcessCameraProvider
//import androidx.compose.animation.ExperimentalAnimationApi
//import androidx.core.content.ContextCompat
//import com.example.jelajahiapp.MainActivity
//import com.example.jelajahiapp.R
//import com.example.jelajahiapp.data.ViewModelFactory
//import com.example.jelajahiapp.databinding.ActivityRecommendationBinding
//import com.example.jelajahiapp.ui.screen.community.reduceFileImage
//import com.example.jelajahiapp.ui.screen.community.uriToFile
//import com.google.accompanist.pager.ExperimentalPagerApi
//import okhttp3.MediaType.Companion.toMediaType
//import okhttp3.MultipartBody
//import okhttp3.RequestBody.Companion.asRequestBody
//
//@OptIn(ExperimentalAnimationApi::class)
//class RecommendationActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityRecommendationBinding
//    private var selectedImageUri: Uri? = null
//    private var imageCapture: ImageCapture? = null
//    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
//    private var cameraProvider: ProcessCameraProvider? = null
//
//    private val viewModel by viewModels<RecommendationViewModel> {
//        ViewModelFactory.getInstance(this)
//    }
//
//    private val requestPermissionLauncher =
//        registerForActivityResult(
//            ActivityResultContracts.RequestPermission()
//        ) { isGranted: Boolean ->
//            if (isGranted) {
//                Toast.makeText(this, getString(R.string.permission_yes), Toast.LENGTH_LONG).show()
//            } else {
//                Toast.makeText(this, getString(R.string.permission_no), Toast.LENGTH_LONG).show()
//            }
//        }
//
//    private fun allPermissionsGranted() =
//        ContextCompat.checkSelfPermission(
//            this,
//            REQUIRED_PERMISSION
//        ) == PackageManager.PERMISSION_GRANTED
//
//
//    @OptIn(ExperimentalPagerApi::class)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityRecommendationBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//
//        if (!allPermissionsGranted()) {
//            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
//        }
//
//        binding.apply {
//            binding.switchCamera.setOnClickListener {
//                switchCamera()
//                startCamera()
//            }
//
//            captureImage.setOnClickListener {
//                takePhoto()
//            }
//
//            openGallery.setOnClickListener {
//                startGallery()
//            }
//
//            btnBack.setOnClickListener {
//                startActivity(Intent(this@RecommendationActivity, MainActivity::class.java))
//            }
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        hideSystemUI()
//        startCamera()
//    }
//
//    private fun startCamera() {
//        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
//
//        cameraProviderFuture.addListener({
//            cameraProvider = cameraProviderFuture.get() // Update the cameraProvider reference
//            val preview = Preview.Builder()
//                .build()
//                .also {
//                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
//                }
//
//            imageCapture = ImageCapture.Builder().build()
//
//            try {
//                cameraProvider?.unbindAll() // Use the cameraProvider reference
//                cameraProvider?.bindToLifecycle(
//                    this,
//                    cameraSelector,
//                    preview,
//                    imageCapture
//                )
//            } catch (exc: Exception) {
//                showToast(getString(R.string.failed_camera))
//            }
//        }, ContextCompat.getMainExecutor(this))
//    }
//
//    private fun switchCamera() {
//        cameraProvider?.unbindAll()
//
//        cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
//            CameraSelector.DEFAULT_FRONT_CAMERA
//        } else {
//            CameraSelector.DEFAULT_BACK_CAMERA
//        }
//    }
//
//    private fun takePhoto() {
//        val imageCapture = imageCapture ?: return
//
//        val photoFile = createFile(application)
//
//        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
//        imageCapture.takePicture(
//            outputOptions,
//            ContextCompat.getMainExecutor(this),
//            object : ImageCapture.OnImageSavedCallback {
//                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
//                    showToast(getString(R.string.capture_image))
//                    selectedImageUri = Uri.fromFile(photoFile)
//                    cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
//                    uploadImage()
//                }
//
//                override fun onError(exception: ImageCaptureException) {
//                    showToast(getString(R.string.failed_capture_image))
//                }
//            }
//        )
//    }
//
//
//    private fun startGallery() {
//        val intent = Intent()
//        intent.action = Intent.ACTION_GET_CONTENT
//        intent.type = "image/*"
//        val chooser = Intent.createChooser(intent, getString(R.string.photo))
//        launcherIntentGallery.launch(chooser)
//    }
//
//    private val launcherIntentGallery = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) { result ->
//        if (result.resultCode == RESULT_OK) {
//            val selectedImg = result.data?.data as Uri
//            selectedImg.let { _ ->
//                selectedImageUri = result.data?.data
//                uploadImage()
//            }
//        }
//    }
//
//    private fun uploadImage() {
//        if (selectedImageUri == null) {
//            showToast(getString(R.string.add_image))
//        } else {
//            selectedImageUri?.let { uri ->
//                val imageFile = uriToFile(uri, this)?.reduceFileImage()
//
//                if (imageFile != null && imageFile.exists()) {
//                    val requestImageFile = imageFile.asRequestBody("image/*".toMediaType())
//                    val imageMultipart = MultipartBody.Part.createFormData("file", imageFile.name, requestImageFile)
//                    viewModel.recommendation(imageMultipart)
//                    finishAffinity()
//                } else {
//                }
//            }
//        }
//    }
//
//    private fun hideSystemUI() {
//        @Suppress("DEPRECATION")
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            window.insetsController?.hide(WindowInsets.Type.statusBars())
//        } else {
//            window.setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN
//            )
//        }
//        supportActionBar?.hide()
//    }
//
//    private fun showToast(message: String) {
//        Toast.makeText(this@RecommendationActivity, message, Toast.LENGTH_SHORT).show()
//    }
//
//
//    companion object {
//        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
//    }
//}
//
//
