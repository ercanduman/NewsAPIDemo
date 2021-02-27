package ercanduman.newsapidemo.util

import android.util.Log
import ercanduman.newsapidemo.BuildConfig

/**
 * Utility functions related actions inside app. Extensions functions that prevents duplicated
 * code blocks.
 *
 * @author ercan
 * @since  2/27/21
 */

fun Any.log(message: String) {
    if (BuildConfig.DEBUG) Log.d(this.javaClass.name, "log: $message")
}