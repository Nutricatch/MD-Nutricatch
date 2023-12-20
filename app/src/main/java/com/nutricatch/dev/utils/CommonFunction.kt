package com.nutricatch.dev.utils

import android.content.Context
import android.widget.Toast
import com.nutricatch.dev.utils.Const.timeStamp
import java.io.File

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun createCustomTempFile(context: Context): File {
    val filesDir = context.externalCacheDir
    return File.createTempFile(timeStamp, ".jpg", filesDir)
}