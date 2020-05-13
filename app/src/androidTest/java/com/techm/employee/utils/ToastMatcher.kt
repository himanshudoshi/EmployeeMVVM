package com.telstra.mvvmdemo.utils

import android.os.IBinder

import android.view.WindowManager
import androidx.test.espresso.Root
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

/**
 * This is Generic ToastMatcher Class
 *
 */
class ToastMatcher : TypeSafeMatcher<Root?>() {
    override fun describeTo(description: Description) {
        description.appendText("is toast")
    }

    override fun matchesSafely(root: Root?): Boolean {
        root?.let { it ->
            val type: Int = it.windowLayoutParams.get().type
            if (type == WindowManager.LayoutParams.TYPE_TOAST) {
                val windowToken: IBinder = it.decorView.windowToken
                val appToken: IBinder = it.decorView.applicationWindowToken
                if (windowToken === appToken) {
                    return true
                }
            }
        }
        return false
    }
}