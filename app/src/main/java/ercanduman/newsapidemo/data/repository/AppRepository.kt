package ercanduman.newsapidemo.data.repository

import ercanduman.newsapidemo.data.network.NewsAPI
import ercanduman.newsapidemo.util.ApiEvent
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
    suspend fun getApiEvent(searchQuery: String): ApiEvent {
        return try {
            val result = api.getArticles(searchQuery)
            if (result.isSuccessful) {
                if (result.body() != null) {
                    ApiEvent.Success(result.body()!!.articles)
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
}