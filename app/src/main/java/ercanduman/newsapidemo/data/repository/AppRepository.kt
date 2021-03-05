package ercanduman.newsapidemo.data.repository

import ercanduman.newsapidemo.data.db.dao.ArticleDao
import ercanduman.newsapidemo.data.network.NewsAPI
import ercanduman.newsapidemo.data.network.model.Article
import ercanduman.newsapidemo.util.ApiEvent
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
class AppRepository @Inject constructor(private val api: NewsAPI, private val dao: ArticleDao) :
    SafeApiCall() {

    /**
     * Gets breaking news from API.
     *
     * Connects NewsAPI, gets data and returns ApiExecutionEvent
     */
    suspend fun getArticles(page: Int): ApiEvent = apiCall { api.getArticles(page = page) }

    /**
     * Searches for articles based on query text.
     */
    suspend fun searchArticles(query: String, page: Int): ApiEvent =
        apiCall { api.searchArticles(query, page) }

    fun getSavedArticles() = dao.getSavedArticles()
    suspend fun insert(article: Article) = dao.insert(article)
    suspend fun delete(article: Article) = dao.delete(article)
}