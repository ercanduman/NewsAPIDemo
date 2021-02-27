package ercanduman.newsapidemo.util

import ercanduman.newsapidemo.data.network.model.Article

/**
 * Handles api data and based on api executions returns classes such as Success, Error,
 * Loading, Empty.
 *
 * @author ercan
 * @since  2/27/21
 */
sealed class ApiEvent(
    private val data: List<Article> = emptyList(),
    private val message: String = ""
) {
    class Success(val data: List<Article>) : ApiEvent(data = data)
    class Error(val message: String) : ApiEvent(message = message)
    object Empty : ApiEvent()
    object Loading : ApiEvent()
}