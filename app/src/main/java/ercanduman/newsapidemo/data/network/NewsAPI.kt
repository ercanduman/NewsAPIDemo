package ercanduman.newsapidemo.data.network

import ercanduman.newsapidemo.BuildConfig
import ercanduman.newsapidemo.Constants
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
 * @author ercanduman
 * @since  27.02.2021
 */
interface NewsAPI {
    /**
     *  "companion object" is used in order to create static variables or functions in Kotlin.
     */
    companion object {
        /* API related constants */
        private const val API_KEY = BuildConfig.NEWS_API_KEY
    }

    /**
     * Gets breaking news from API based on country code.
     *
     * Page query parameter (@Query("page")) added in order to load articles page by page
     * which refers to Pagination functionality.
     *
     * Parameters:
     *
     * [countryCode]: The 2-letter ISO 3166-1 code of the country you want to get headlines for. i.e. us
     *
     * [page]: Use this to page through the results if the total results found is greater than the page size.
     *
     * [pageSize]: The number of results to return per page (request). 20 is the default, 100 is the maximum.
     *
     * @return NewsAPIResponse object with retrofit2.Response which then can be handled for success
     * or failure calls.
     */
    @GET("top-headlines")
    suspend fun getArticles(
        @Query("country") countryCode: String = Constants.DEFAULT_COUNTRY_CODE,
        @Query("page") page: Int = Constants.DEFAULT_PAGE,
        @Query("pageSize") pageSize: Int = Constants.DEFAULT_PAGE_SIZE,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsAPIResponse>

    /**
     * Searches for news from API and get data based on search query.
     *
     * As query parameters; country, page, pageSize, language, source, etc can be used. For all
     * parameters https://newsapi.org/docs/endpoints/everything url can be visited.
     *
     * Parameters:
     *
     * [query]: Keywords or phrases to search for in the article title and body.
     *
     * [page]: Use this to page through the results. Default: 1
     *
     * [pageSize]: The number of results to return per page. Default: 20. Maximum: 100.
     *
     * @return NewsAPIResponse object with retrofit2.Response which then can be handled for success
     * or failure calls.
     */
    @GET("everything")
    suspend fun searchArticles(
        @Query("q") query: String,
        @Query("page") page: Int = Constants.DEFAULT_PAGE,
        @Query("pageSize") pageSize: Int = Constants.DEFAULT_PAGE_SIZE,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsAPIResponse>
}