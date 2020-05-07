package com.telstra.telstramvvm.utils

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import com.google.android.material.snackbar.Snackbar

/**
 * extension functions
 * **/
fun Context.toast(message: String){
    toast( message )
}
fun ProgressBar.show(){
    visibility = View.VISIBLE
}

fun ProgressBar.hide(){
    visibility = View.GONE
}
