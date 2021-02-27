package ercanduman.newsapidemo.util

import android.content.Context
import android.view.View
import android.widget.Toast

/**
 * Contains View and Context related extension functions.
 *
 * @author ercan
 * @since  2/27/21
 */

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}