package com.nutricatch.dev.model

import com.nutricatch.dev.R

data class SettingMenu(
    val drawableIcon: Int,
    val title: String,
)

val settingMenu = listOf(
    SettingMenu(R.drawable.ic_scale, "My Weight"),
    SettingMenu(R.drawable.ic_earth, "Language"),
    SettingMenu(R.drawable.ic_dark, "Dark Theme"),
    SettingMenu(R.drawable.ic_share, "Share App"),
    SettingMenu(R.drawable.ic_mail, "Contact Us"),
    SettingMenu(R.drawable.ic_help, "Help"),
    SettingMenu(R.drawable.ic_shield, "Privacy & Policy"),
    SettingMenu(R.drawable.ic_setting, "Settings")
)
