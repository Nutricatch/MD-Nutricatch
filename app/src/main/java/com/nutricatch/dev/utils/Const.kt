package com.nutricatch.dev.utils

import android.Manifest

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

    /*
    * Permission Related
    * */
    const val FINE_LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION
    const val COARSE_LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION
}