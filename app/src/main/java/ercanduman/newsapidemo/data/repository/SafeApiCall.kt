package ercanduman.newsapidemo.data.repository

import ercanduman.newsapidemo.data.network.response.NewsAPIResponse
import ercanduman.newsapidemo.util.ApiEvent
import retrofit2.Response

/**
 * Surround API call with try-catch block and returns and ApiEvent based on execution result.
 *
 * @author ercanduman
 * @since  05.03.2021
 */
abstract class SafeApiCall {
    /**
     * Handles API response based on result and returns respective ApiEvent
     */
    internal suspend fun apiCall(apiCall: suspend () -> Response<NewsAPIResponse>): ApiEvent =
        try {
            val result = apiCall.invoke()
            if (result.isSuccessful) {
                val resultBody = result.body()
                if (resultBody != null && resultBody.articles.isNotEmpty()) {
                    ApiEvent.Success(resultBody.articles)
                } else {
                    ApiEvent.Empty
                }
            } else {
                ApiEvent.Error("Code: ${result.code()} Error: ${result.message()} - ${result.errorBody()}")
            }
        } catch (e: Exception) {
            ApiEvent.Error(e.message ?: "An unknown error occurred...")
        }
}