package com.nutricatch.dev.utils

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import com.nutricatch.dev.utils.Const.timeStamp
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun createCustomTempFile(context: Context): File {
    val filesDir = context.cacheDir
    return File.createTempFile(timeStamp, ".jpg", filesDir)
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
private fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("GMT+0:00") // Set timezone to GMT+7 Jakarta

    val calendar = Calendar.getInstance()
    return dateFormat.format(calendar.time)
}

// Usage
val todayDate = getCurrentDate()
