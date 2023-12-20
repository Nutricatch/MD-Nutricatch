package com.nutricatch.dev.utils

import android.Manifest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/*
* File ini berisi variabel global yg digunakan di aplikasi
* */

object Const {
    const val TOKEN_NAME = "token"
    const val ON_BOARD = "on-board"
    const val THEME = "theme_mode"
    const val LOCALE = "locale"
    const val LOCALE_ID = "in-ID"
    const val LOCALE_EN = "en-US"
    const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
    val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())
}

object Permissions {
    /*
    * Permission Related
    * */
    const val FINE_LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION
    const val COARSE_LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION
    const val CAMERA_PERMISSION = Manifest.permission.CAMERA
    const val EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
}