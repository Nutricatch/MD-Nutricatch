package com.nutricatch.dev.views.navigation.camera

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.common.util.concurrent.ListenableFuture
import com.nutricatch.dev.databinding.ActivityCameraBinding
import com.nutricatch.dev.helper.MLHelper
import com.nutricatch.dev.utils.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private lateinit var executorService: ExecutorService
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraProvidedFuture: ListenableFuture<ProcessCameraProvider>
    private var uri: Uri? = null

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            stopCamera()
            this.uri = uri
            val result = mlHelper.processImage(this, uri)
            showToast(this, result)
            showPreviewImage(this.uri!!)
            showRetakeButton()
        }
    }

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permission ->
            // Handle Permission granted/rejected
            var permissionGranted = true
            permission.entries.forEach {
                if (it.key in REQUIRED_PERMISSIONS && !it.value) permissionGranted = false
            }
            if (!permissionGranted) {
                Toast.makeText(
                    baseContext,
                    "Permission request denied",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                startCamera()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cameraProvidedFuture = ProcessCameraProvider.getInstance(this)
        executorService = Executors.newSingleThreadExecutor()

        // Request Camera Permission
        if (allPermissionGranted()) {
            startCamera()
        } else {
            requestPermissions()
        }

        binding.btnCapture.setOnClickListener {
            capture()
        }

        binding.btnRetake.setOnClickListener {
            startCamera()
            hideRetakeButton()
            hidePreviewImage()
        }

        binding.btnPickImage.setOnClickListener {
            startGallery()
        }

        binding.btnNext.setOnClickListener{
            
        }
    }

    override fun onRestart() {
        super.onRestart()
        startCamera()
        hideRetakeButton()
        hidePreviewImage()
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun capture() {
        // get a stable reference of modifiable image capture
        val imageCapture = imageCapture ?: return

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(executorService,
            object : ImageCapture.OnImageSavedCallback, OnImageCapturedCallback() {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {}

                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)
                    val result = mlHelper.processImageProxy(this@CameraActivity, image)
                    lifecycleScope.launch {
                        withContext(Dispatchers.Main) {
                            showToast(this@CameraActivity, result)
                            stopCamera()
                            showRetakeButton()
                        }
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    val msg = "Something error"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "Photo capture failed: ${exception.message}", exception)
                }

            })
    }

    private fun startCamera() {
        cameraProvidedFuture.addListener({
            val cameraProvider = cameraProvidedFuture.get()
            val preview = Preview.Builder().build()
                .also { it.setSurfaceProvider(binding.viewFinder.surfaceProvider) }
            imageCapture = ImageCapture.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // unbind camera before rebinding
                cameraProvider.unbindAll()

                // bind camera
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (e: Exception) {
                Log.e(TAG, "Use Case Binding Failed", e)
            }
        }, ContextCompat.getMainExecutor(this))

    }

    private fun stopCamera() {
        val cameraProvider = cameraProvidedFuture.get()
        cameraProvider.unbindAll()
        showRetakeButton()
    }

    private fun showRetakeButton() {
        with(binding) {
            btnRetake.visibility = View.VISIBLE
            btnNext.visibility = View.VISIBLE
            btnCapture.visibility = View.GONE
        }
    }

    private fun hideRetakeButton() {
        with(binding) {
            btnRetake.visibility = View.GONE.also {
                btnNext.visibility = it
            }
            btnCapture.visibility = View.VISIBLE
        }
    }

    private fun restartCamera()
    {
        val cameraProvider = cameraProvidedFuture.get()
        cameraProvider.unbindAll()
        binding.btnCapture.visibility = View.VISIBLE
        binding.btnNext.visibility = View.INVISIBLE
        binding.btnNext.visibility = View.INVISIBLE
    }

    private fun requestPermissions() {
        activityResultLauncher.launch(REQUIRED_PERMISSIONS)
    }

    private fun allPermissionGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun showPreviewImage(uri: Uri) {
        binding.imgPreview.setImageURI(uri)
        binding.viewFinder.visibility = View.INVISIBLE
        binding.imgPreview.visibility = View.VISIBLE
    }

    private fun hidePreviewImage() {
        binding.viewFinder.visibility = View.VISIBLE
        binding.imgPreview.visibility = View.GONE
        hideRetakeButton()
    }

    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private val mlHelper = MLHelper()
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}