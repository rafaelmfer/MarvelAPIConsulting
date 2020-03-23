package com.rafaelmfer.marvelcharactersconsultation.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment

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

/**
 *  Função para esconder o teclado seja em Activity, Fragment, View, Dialog, etc.
 */

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.hideKeyboard() {
    if (currentFocus == null) View(this) else currentFocus?.let { hideKeyboard(it) }
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

