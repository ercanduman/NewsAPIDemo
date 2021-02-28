package ercanduman.newsapidemo

/**
 * Contains all constant variables that can be used in application.
 *
 * If Constants is created as class with companion object, then compiler will generate getter
 * and setter for fields under the hood. For this reason, Constants should be "object" type.
 *
 * @author ercan
 * @since  2/27/21
 */
object Constants {
    internal const val API_BASE_URL = "https://newsapi.org/v2/"

    internal const val SEARCH_TIME_DELAY = 500L
}