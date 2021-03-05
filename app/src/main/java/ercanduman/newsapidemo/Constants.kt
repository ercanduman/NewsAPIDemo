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

    /* API related constants */
    internal const val BASE_URL = "https://newsapi.org/v2/"
    internal const val DEFAULT_URL = "https://github.com/ercanduman/NewsAPIDemo"
    internal const val DEFAULT_PAGE = 1
    internal const val DEFAULT_MAX_SIZE = 100
    internal const val DEFAULT_PAGE_SIZE = 20
    internal const val DEFAULT_COUNTRY_CODE = "us"

    /* DB related constants */
    internal const val DATABASE_NAME = "app_database"
    internal const val DATABASE_VERSION = 1
}