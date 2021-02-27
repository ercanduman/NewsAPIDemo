package ercanduman.newsapidemo.data.network

import ercanduman.newsapidemo.BuildConfig
import ercanduman.newsapidemo.data.network.model.NewsAPIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrieves data from News API and contains HTTP related functions.
 *
 * @author ercan
 * @since  2/27/21
 */
interface NewsAPI {

    /**
     * top-headlines – Gets data for breaking news headlines for countries, categories,
     * and singular publishers.
     *
     * @return NewsAPIResponse object with retrofit2.Response which then can be handled for success
     * or failure calls.
     */
    @GET("top-headlines")
    suspend fun getArticles(
        @Query("q") searchQuery: String,
        @Query("apiKey") apiKey: String = BuildConfig.APPLICATION_ID // TODO: 2/27/21 replace with API_KEY from build gradle
    ): Response<NewsAPIResponse>
}