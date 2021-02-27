package ercanduman.newsapidemo.util

import ercanduman.newsapidemo.data.network.model.Article

/**
 * Handles api data and based on api executions returns classes such as Success, Error,
 * Loading, Empty.
 *
 * @author ercan
 * @since  2/27/21
 */
sealed class ApiExecutionEvent(val data: List<Article> = emptyList(), val message: String = "") {
    class Success(data: List<Article>) : ApiExecutionEvent(data = data)
    class Error(message: String) : ApiExecutionEvent(message = message)
    object Empty : ApiExecutionEvent()
    object Loading : ApiExecutionEvent()
}