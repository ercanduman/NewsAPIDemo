package ercanduman.newsapidemo.data.network

import ercanduman.newsapidemo.BuildConfig
import ercanduman.newsapidemo.data.network.response.NewsAPIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrieves data from News API and contains HTTP related functions.
 *
 * top-headlines – Gets data for breaking news headlines for countries, categories,
 * and singular publishers.
 *
 * everything – Searches every article published by different sources. This endpoint is
 * ideal for news analysis and article discovery.
 *
 * @author ercan
 * @since  2/27/21
 */
interface NewsAPI {

    /**
     * Gets breaking news from API based on country code.
     *
     * Page query parameter (@Query("page")) added in order to load articles page by page
     * which refers to Pagination functionality.
     *
     * @return NewsAPIResponse object with retrofit2.Response which then can be handled for success
     * or failure calls.
     */
    @GET("top-headlines")
    suspend fun getArticles(
        @Query("country") countryCode: String = "us",
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String = BuildConfig.NEWS_API_KEY
    ): Response<NewsAPIResponse>

    /**
     * Searches for news from API and get data based on search query.
     *
     * As query parameters; country, page, pageSize, language, source, etc can be used. For all
     * parameters https://newsapi.org/docs/endpoints/everything url can be visited.
     *
     * @return NewsAPIResponse object with retrofit2.Response which then can be handled for success
     * or failure calls.
     */
    @GET("everything")
    suspend fun searchArticles(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String = BuildConfig.NEWS_API_KEY
    ): Response<NewsAPIResponse>
}