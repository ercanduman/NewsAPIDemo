package ercanduman.newsapidemo.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

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

fun View.snackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}

/**
 * Displays a snackbar with action and when action button click [actionFunction] will be invoked.
 */
fun View.snackbarAction(message: String, actionText: String, actionFunction: () -> Unit) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).apply {
        setAction(actionText) { actionFunction.invoke() }
        show()
    }
}

fun Context.isOnline(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val activeNetwork = connectivityManager.activeNetwork
    val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
    return if (networkCapabilities != null) {
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    } else false
}