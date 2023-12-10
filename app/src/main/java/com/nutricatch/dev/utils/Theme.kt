package com.nutricatch.dev.utils

sealed class Theme {
    data object Light : Theme()
    data object Dark : Theme()
}