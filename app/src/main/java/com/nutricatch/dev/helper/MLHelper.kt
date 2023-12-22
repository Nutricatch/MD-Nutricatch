package com.nutricatch.dev.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.net.Uri
import com.nutricatch.dev.ml.SaveModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

data class ScanResult(
    var label: String = "unknown",
    var isRecognized: Boolean = true
)

class MLHelper {
    private fun loadBitmapFromUri(context: Context, uri: Uri): Bitmap? {
        return try {
            BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri))
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Function to preprocess the bitmap for model input
    private fun preprocessBitmap(bitmap: Bitmap): ByteBuffer {
        val modelInputSize = 224 // Change this based on your model's input size
        val inputShape = intArrayOf(1, modelInputSize, modelInputSize, 3)
        val inputBuffer =
            ByteBuffer.allocateDirect(4 * inputShape[0] * inputShape[1] * inputShape[2] * inputShape[3])
        inputBuffer.order(ByteOrder.nativeOrder())

        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, modelInputSize, modelInputSize, true)

        // Normalize pixel values to range between 0 and 1
        for (y in 0 until modelInputSize) {
            for (x in 0 until modelInputSize) {
                val pixelValue = resizedBitmap.getPixel(x, y)
                inputBuffer.putFloat(((pixelValue shr 16 and 0xFF) - 127.0f) / 128.0f)
                inputBuffer.putFloat(((pixelValue shr 8 and 0xFF) - 127.0f) / 128.0f)
                inputBuffer.putFloat(((pixelValue and 0xFF) - 127.0f) / 128.0f)
            }
        }
        return inputBuffer
    }

    // Function to process the image using the TFLite model
    fun processImage(context: Context, uri: Uri): ScanResult {
        val model = SaveModel.newInstance(context)

        // Load bitmap from URI
        val bitmap = loadBitmapFromUri(context, uri)

        val scanResult = ScanResult()

        bitmap?.let {
            // Preprocess the bitmap
            val inputBuffer = preprocessBitmap(bitmap)

            // Create inputs for reference
            val inputFeature0 =
                TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(inputBuffer)

            // Runs model inference and gets result
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

            // Handle output as needed
            // ...
            val tab = outputFeature0.floatArray

            val probability = tab.max()
            val index = tab.indexOfFirst { it == probability }

            val strings = labelResult

            if (probability > 0.4) {
                strings[index]
                scanResult.label = strings[index]
                scanResult.isRecognized = true
            } else {
                scanResult.label = "Image not recognized"
                scanResult.isRecognized = false
            }
            // Release model resources if no longer used
        }
        model.close()

        return scanResult
    }

    // Function to convert Image to Bitmap
    private fun imageToBitmap(image: Image): Bitmap {
        val buffer = image.planes[0].buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }
}