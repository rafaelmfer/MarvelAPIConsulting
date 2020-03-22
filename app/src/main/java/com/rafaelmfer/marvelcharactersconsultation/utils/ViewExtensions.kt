package com.rafaelmfer.marvelcharactersconsultation.utils

import android.view.MotionEvent
import android.view.View

fun View.setToolbarAccessibleBackButton() {
    setOnTouchListener { view, event ->
        when (event.action) {
            MotionEvent.ACTION_UP -> {
                view.performClick()
                true
            }
            else -> false
        }
    }
}

fun View.changeVisibility(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.changeToInvisible(invisible: Boolean) {
    this.visibility = if (invisible) View.INVISIBLE else View.VISIBLE
}