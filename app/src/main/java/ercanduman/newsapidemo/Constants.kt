package ercanduman.newsapidemo

/**
 * Contains all constant variables that can be used in application.
 *
 * If Constants is created as class with companion object, then compiler will generate getter
 * and setter for fields under the hood. For this reason, Constants should be "object" type.
 *
 * internal: access modifier that will be visible to entire application/module.
 *
 * @author ercan
 * @since  2/27/21
 */
object Constants {

    /* UI related constants */
    internal const val SEARCH_TIME_DELAY = 500L

    /* DB related constants */
    internal const val DATABASE_NAME = "app_database"
    internal const val DATABASE_VERSION = 1
}