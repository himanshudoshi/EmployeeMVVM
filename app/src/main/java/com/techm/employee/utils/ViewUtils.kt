package com.techm.employee.utils

import android.content.Context
import android.view.View
import android.widget.ProgressBar

/** Extension function to show Toast Messages. */
fun Context.toast(message: String) {
    toast(message)
}

/** Extension function to show ProgressBar. */
fun ProgressBar.show() {
    visibility = View.VISIBLE
}

/** Extension function to hide ProgressBar. */
fun ProgressBar.hide() {
    visibility = View.GONE
}
