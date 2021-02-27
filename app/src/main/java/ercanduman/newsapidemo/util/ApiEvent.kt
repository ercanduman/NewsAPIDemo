package ercanduman.newsapidemo.util

import ercanduman.newsapidemo.data.network.model.Article

/**
 * Handles api data and based on api executions returns classes such as Success, Error,
 * Loading, Empty.
 *
 * @author ercan
 * @since  2/27/21
 */
sealed class ApiEvent(val data: List<Article> = emptyList(), val message: String = "") {
    class Success(data: List<Article>) : ApiEvent(data = data)
    class Error(message: String) : ApiEvent(message = message)
    object Empty : ApiEvent()
    object Loading : ApiEvent()
}