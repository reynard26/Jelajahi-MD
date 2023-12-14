package com.example.jelajahiapp.ui.screen.community

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.jelajahiapp.R
import com.example.jelajahiapp.data.ViewModelFactory
import com.example.jelajahiapp.databinding.ActivityAddCommunityBinding
import com.example.jelajahiapp.ui.screen.community.viewmodel.CommunityViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AddCommunityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCommunityBinding
    private var currentImageUri: Uri? = null

    private val viewModel by viewModels<CommunityViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val KEY_CURRENT_IMAGE_URI = "current_image_uri"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCommunityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(!allPermissionsGranted()){
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.tvAddPhoto.setOnClickListener {
            val alertDialog: AlertDialog? = this.let {  // Change requireActivity() to this
                val builder = AlertDialog.Builder(it)
                builder.apply {
                    setPositiveButton("Image") { dialog, id -> startGallery() }
                    setNegativeButton("Camera") { dialog, id -> startCamera() }
                }
                builder.create()
            }
            alertDialog?.show()
        }

        if (savedInstanceState != null) {
            // Restore the currentImageUri if available
            currentImageUri = savedInstanceState.getParcelable(KEY_CURRENT_IMAGE_URI)
            showImage()
        }

    }

    private fun pushToken() {
        viewModel.getToken().observe(this@AddCommunityActivity) { token ->
            uploadImage(token!!)
        }

    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, getString(R.string.permission_yes), Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, getString(R.string.permission_no), Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED


    private fun uploadImage(token: String) {
        if (currentImageUri == null) {
            showToast(getString(R.string.add_image))
        } else {

            currentImageUri?.let { uri ->
                val imageFile = uriToFile(uri, this).reduceFileImage()

                val description = binding.edDescription.text.toString()
                    .toRequestBody("text/plain".toMediaType())
                val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "photo",
                    imageFile.name,
                    requestImageFile
                )
//                viewModel.addStory(token, imageMultipart, description)
//                informationProvider()
                finishAffinity()
            }
        }
    }


    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.tvAddPhoto.setImageURI(it)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        when (isLoading) {
            true -> {
                binding.PGADD.visibility = View.VISIBLE
            }

            else -> {
                binding.PGADD.visibility = View.GONE
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the currentImageUri in the bundle
        outState.putParcelable(KEY_CURRENT_IMAGE_URI, currentImageUri)
    }


    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}