package ercanduman.newsapidemo.data.repository

import ercanduman.newsapidemo.data.network.NewsAPI
import ercanduman.newsapidemo.util.ApiExecutionEvent
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
    suspend fun getArticles(searchQuery: String): ApiExecutionEvent {
        return try {
            val result = api.getArticles(searchQuery)
            if (result.isSuccessful) {
                if (result.body() != null) {
                    ApiExecutionEvent.Success(result.body()!!.articles)
                } else {
                    ApiExecutionEvent.Empty
                }
            } else {
                ApiExecutionEvent.Error("Error: ${result.message()} - ${result.errorBody()} Code: ${result.code()}")
            }
        } catch (e: Exception) {
            ApiExecutionEvent.Error(e.message ?: "An unknown error occurred...")
        }
    }
}