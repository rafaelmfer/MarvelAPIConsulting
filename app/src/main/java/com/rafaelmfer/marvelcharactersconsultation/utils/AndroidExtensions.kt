package com.rafaelmfer.customviews.extensions

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.annotation.ColorRes

const val STATUS_BAR_HEIGHT = "status_bar_height"
const val DIMENSION = "dimen"
const val SYSTEM_NAME = "android"

fun Activity.setStatusBarColor(@ColorRes colorId: Int, hasLightTextColor: Boolean = true) {
    window.statusBarColor = resources.getColor(colorId)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (hasLightTextColor) window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}

fun View.getStatusBarHeight(): Int {
    var result = 0
    val resourceId = resources.getIdentifier(STATUS_BAR_HEIGHT, DIMENSION, SYSTEM_NAME)
    if (resourceId > 0) result = resources.getDimensionPixelOffset(resourceId)
    return result
}

