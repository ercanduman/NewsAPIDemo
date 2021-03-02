package ercanduman.newsapidemo.util

import ercanduman.newsapidemo.data.network.model.Article

/**
 * Handles api data and based on api executions returns classes such as Success, Error,
 * Loading, Empty.
 *
 * @author ercan
 * @since  2/27/21
 */
sealed class ApiEvent {
    class Success(val data: List<Article>) : ApiEvent()
    class Error(val message: String) : ApiEvent()
    object Empty : ApiEvent()
    object Loading : ApiEvent()
}