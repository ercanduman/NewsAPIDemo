package ercanduman.newsapidemo.data.internal

import ercanduman.newsapidemo.data.network.response.NewsAPIResponse
import ercanduman.newsapidemo.util.ApiEvent
import retrofit2.Response

/**
 * Surround API call with try-catch block, handles API response and returns an ApiEvent based on
 * execution result.
 *
 * Inline functions are used to save us memory overhead by preventing object allocations for
 * the anonymous functions/lambda expressions called. Instead, it provides that functions body to
 * the function that calls it at runtime. This increases the bytecode size slightly but saves us
 * a lot of memory.
 *
 * @author ercanduman
 * @since  05.03.2021
 */
inline fun safeApiCall(apiCall: () -> Response<NewsAPIResponse>): ApiEvent =
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
            ApiEvent.Error("Code: ${result.code()} - Error: ${result.message()} - ${result.errorBody()}")
        }
    } catch (e: Exception) {
        ApiEvent.Error(e.message ?: "An unknown error occurred...")
    }
