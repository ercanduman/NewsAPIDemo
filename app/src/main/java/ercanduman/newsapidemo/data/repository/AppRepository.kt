package ercanduman.newsapidemo.data.repository

import ercanduman.newsapidemo.data.db.dao.ArticleDao
import ercanduman.newsapidemo.data.network.NewsAPI
import ercanduman.newsapidemo.data.network.model.Article
import ercanduman.newsapidemo.data.network.response.NewsAPIResponse
import ercanduman.newsapidemo.util.ApiEvent
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Abstraction layer which is a mediator and responsible to retrieve data from different data
 * sources and provides data for ViewModel. For example, different data sources can be Room database
 * for caching or Retrofit for remote networking data.
 *
 * Annotating with @Inject means this constructor is injectable and accept zero or more dependencies
 * as arguments. @Inject can apply to at most one constructor per class.
 *
 * Here "Constructor Injection" used instead of "Field Injection". Because when field injection used,
 * later on it cannot be clear what objects injected in Repository and classes will be tightly
 * coupled. For more drawbacks of field Injection: https://stackoverflow.com/a/39892204/4308897
 *
 * After making the repository as @Singleton and inject it in ViewModels, any ViewModel now has
 * access to that specific repository.
 *
 * @author ercanduman
 * @since  27.02.2021
 */
@Singleton
class AppRepository @Inject constructor(private val api: NewsAPI, private val dao: ArticleDao) {

    /**
     * Gets breaking news from API.
     *
     * Connects NewsAPI, gets data and returns ApiExecutionEvent
     */
    suspend fun getArticles(page: Int): ApiEvent = safeApiCall { api.getArticles(page = page) }

    /**
     * Searches for articles based on query text.
     */
    suspend fun searchArticles(query: String, page: Int): ApiEvent =
        safeApiCall { api.searchArticles(query, page) }

    /**
     * Handles API response based on result and returns respective ApiEvent
     */
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
                ApiEvent.Error("Code: ${result.code()} Error: ${result.message()} - ${result.errorBody()}")
            }
        } catch (e: Exception) {
            ApiEvent.Error(e.message ?: "An unknown error occurred...")
        }

    fun getSavedArticles() = dao.getSavedArticles()
    suspend fun insert(article: Article) = dao.insert(article)
    suspend fun delete(article: Article) = dao.delete(article)
}