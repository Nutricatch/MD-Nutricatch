package com.nutricatch.dev.views.navigation.camera

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.common.util.concurrent.ListenableFuture
import com.nutricatch.dev.R
import com.nutricatch.dev.databinding.FragmentCameraBinding
import com.nutricatch.dev.helper.MLHelper
import com.nutricatch.dev.helper.foodLabelsMap
import com.nutricatch.dev.utils.Permissions
import com.nutricatch.dev.utils.createCustomTempFile
import com.nutricatch.dev.utils.showToast
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraFragment : Fragment() {
    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!

    private lateinit var executorService: ExecutorService
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraProvidedFuture: ListenableFuture<ProcessCameraProvider>
    private var uri: Uri? = null
    private var isFromGallery = false

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            stopCamera()
            this.uri = uri
            /*
            * Show scan result here
            * */

            val resultLabel = mlHelper.processImage(requireContext(), uri)
            val result = foodLabelsMap[resultLabel.label]
            Log.d(TAG, "result: $result")
            if (resultLabel.isRecognized) {
                isFromGallery = true
                showSuccessCard(result ?: "unknown food, we will update it immediately")
                showPreviewImage(this.uri!!)
            } else {
                showToast(requireContext(), "Food is not recognized, try again")
                startCamera()
            }
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
                showToast(requireContext(), "Permission request denied")
            } else {
                startCamera()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraProvidedFuture = ProcessCameraProvider.getInstance(requireContext())
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
            showCaptureButton()
            hidePreviewImage()
            hideSuccessCard()
        }

        binding.btnPickImage.setOnClickListener {
            startGallery()
        }

        binding.btnNext.setOnClickListener {
            hideSuccessCard()
            val action = CameraFragmentDirections.actionCameraFragmentToFoodDetailFragment("$uri")
            action.isfromGallery = isFromGallery
            findNavController().navigate(action)
        }

    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun capture() {
        // get a stable reference of modifiable image capture
        val imageCapture = imageCapture ?: return

        val photoFile = createCustomTempFile(requireContext().applicationContext)

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback, ImageCapture.OnImageCapturedCallback() {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    uri = outputFileResults.savedUri
                    if (uri != null) {
                        val resultLabel = mlHelper.processImage(requireContext(), uri!!)
                        val result = foodLabelsMap[resultLabel.label]
                        stopCamera()
                        if (resultLabel.isRecognized) {
                            isFromGallery = false
                            showSuccessCard(result ?: "Unknown food, we will update it immediately")
                        } else {
                            showToast(requireContext(), "Food is not recognized, try again")
                            startCamera()
                            showCaptureButton()
                            /// delete temp iamge
                            val filePath = getRealPathFromUri(requireContext(), uri!!)
                            if (filePath != null) File(filePath).delete()
                        }
                    }
                }

//                override fun onCaptureSuccess(image: ImageProxy) {
//                    super.onCaptureSuccess(image)
//                    val resultLabel = mlHelper.processImageProxy(requireContext(), image)
//                    val result = foodLabelsMap[resultLabel.label]
//                    lifecycleScope.launch {
//                        withContext(Dispatchers.Main) {
//                            stopCamera()
//                            if (resultLabel.isRecognized) {
//                                isFromGallery = false
//                                showSuccessCard(
//                                    result ?: "unknown food, we will update it immediately"
//                                )
//                            } else {
//                                showToast(requireContext(), "Food is not recognized, try again")
//                                startCamera()
//                                showCaptureButton()
//                                val filePath = getRealPathFromUri(requireContext(), uri!!)
//                                File(filePath).delete()
//                            }
//                        }
//                    }
//                }

                override fun onError(exception: ImageCaptureException) {
                    val msg = "Something error"
                    showToast(requireContext(), msg)
                    Log.e(TAG, "Photo capture failed: ${exception.message}", exception)
                }

            })
    }

    fun getRealPathFromUri(context: Context, uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            it.moveToFirst()
            return it.getString(columnIndex)
        }
        return null
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
        }, ContextCompat.getMainExecutor(requireContext()))

    }

    private fun stopCamera() {
        val cameraProvider = cameraProvidedFuture.get()
        cameraProvider.unbindAll()
        hideCaptureButton()
    }

    private fun hideCaptureButton() {
        with(binding) {
            btnCapture.visibility = View.GONE
        }
    }

    private fun showCaptureButton() {
        with(binding) {
            btnCapture.visibility = View.VISIBLE
        }
    }

    private fun requestPermissions() {
        activityResultLauncher.launch(REQUIRED_PERMISSIONS)
    }

    private fun allPermissionGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private fun showPreviewImage(uri: Uri) {
        binding.imgPreview.setImageURI(uri)
        binding.viewFinder.visibility = View.INVISIBLE
        binding.imgPreview.visibility = View.VISIBLE
    }

    private fun hidePreviewImage() {
        binding.viewFinder.visibility = View.VISIBLE
        binding.imgPreview.visibility = View.GONE
        showCaptureButton()
    }

    private fun showSuccessCard(label: String) {
        binding.cardConfirmation.visibility = View.VISIBLE
        binding.tvConfirmation.text = getString(R.string.confirmation_text, label)
    }

    private fun hideSuccessCard() {
        binding.cardConfirmation.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "CameraXApp"
        private val mlHelper = MLHelper()
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Permissions.CAMERA_PERMISSION
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Permissions.EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }

}