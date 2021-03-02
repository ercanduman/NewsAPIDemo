package ercanduman.newsapidemo.data.repository

import ercanduman.newsapidemo.data.network.NewsAPI
import ercanduman.newsapidemo.data.network.response.NewsAPIResponse
import ercanduman.newsapidemo.util.ApiEvent
import retrofit2.Response
import javax.inject.Inject

/**
 * The middle class that connects to different data sources and provides data for ViewModel.
 *
 * @author ercan
 * @since  2/27/21
 */
class AppRepository @Inject constructor(private val api: NewsAPI) {

    /**
     * Connects NewsAPI, gets data and returns ApiExecutionEvent
     */
    suspend fun getArticles(page: Int): ApiEvent = safeApiCall { api.getArticles(page = page) }

    suspend fun searchArticles(query: String, page: Int): ApiEvent =
        safeApiCall { api.searchArticles(query, page) }

    private suspend fun safeApiCall(apiCall: suspend () -> Response<NewsAPIResponse>): ApiEvent =
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
                ApiEvent.Error("Error: ${result.message()} - ${result.errorBody()} Code: ${result.code()}")
            }
        } catch (e: Exception) {
            ApiEvent.Error(e.message ?: "An unknown error occurred...")
        }
}