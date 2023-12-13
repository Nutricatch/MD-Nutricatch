package com.nutricatch.dev.views.navigation.camera

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.google.common.util.concurrent.ListenableFuture
import com.nutricatch.dev.databinding.ActivityCameraBinding
import com.nutricatch.dev.ml.SaveModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.ExecutorService

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private lateinit var executorService: ExecutorService
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraProvidedFuture: ListenableFuture<ProcessCameraProvider>
    private var uri: Uri? = null

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
        }
    }

    private fun capture() {
        // get a stable reference of modifiable image capture
        val imageCapture = imageCapture ?: return

        // create timestamped name and MediaStore entry
        val photoFile = createTempFile(applicationContext)
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    uri = outputFileResults.savedUri
                    val msg = "Photo capture succeeded: $uri"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                    stopCamera()
                    process()
//                    PreviewImageDialogFragment().show(supportFragmentManager, "Preview Dialog")
                }

                override fun onError(exception: ImageCaptureException) {
                    val msg = "Something error"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "Photo capture failed: ${exception.message}", exception)
                }

            })
    }

    private fun createTempFile(context: Context): File {
        val filesDir = context.externalCacheDir
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())
        return File.createTempFile(name, ".jpg", filesDir)
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
        binding.btnCapture.visibility = View.INVISIBLE
        binding.btnNext.visibility = View.VISIBLE
        binding.btnRetake.visibility = View.VISIBLE
    }

    private fun requestPermissions() {
        activityResultLauncher.launch(REQUIRED_PERMISSIONS)
    }

    private fun allPermissionGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun process() {
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
        val resizeBitmap: Bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
        val model = SaveModel.newInstance(this)

        val bytebuffer = ByteBuffer.allocateDirect(4 * 224 * 224 * 3)
        bytebuffer.order(ByteOrder.nativeOrder())
        val intValues = IntArray(224 * 224)
        bitmap.getPixels(
            intValues,
            0,
            resizeBitmap.width,
            0,
            0,
            resizeBitmap.width,
            resizeBitmap.height
        )
        var pixel = 0
        for (i in 0..223) {
            for (j in 0..223) {
                val tmpVal = intValues[pixel++]
                bytebuffer.putFloat(((tmpVal shr 16) and 0xFF) * (1.0f / 1))
                bytebuffer.putFloat(((tmpVal shr 8) and 0xFF) * (1.0f / 1))
                bytebuffer.putFloat((tmpVal and 0xFF) * (1.0f / 1))
            }
        }
        val inputFeature0 =
            TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
        inputFeature0.loadBuffer(bytebuffer)
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer
        val tab = outputFeature0.floatArray
        for (e in tab) {
            Log.d(TAG, "processImage: $e")
        }
        val biggest = tab.max()
        val index = tab.indexOfFirst { it == biggest }

        val strings = arrayOf(
            "apel",
            "ayam_goreng",
            "ayam_pop",
            "bakso_kuah",
            "cumi_tepung",
            "daging_rendang",
            "gado_gado",
            "gulai_ikan",
            "ikan_goreng",
            "kacang_rebus",
            "kentang_goreng",
            "lemon",
            "mangga",
            "mie_goreng",
            "mie_kuah",
            "nanas",
            "nasi_goreng",
            "opor_ayam",
            "pear",
            "pempek",
            "pisang",
            "sate_bakar",
            "sayur_asam",
            "sayur_bayam",
            "semangka",
            "somay",
            "tahu_goreng",
            "telur_balado",
            "telur_dadar",
            "tempe_goreng",
        )

        Log.d(
            TAG,
            "processImage: ${outputFeature0.dataType} $biggest Index $index ${strings[index]}"
        )
        model.close()
    }

    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
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