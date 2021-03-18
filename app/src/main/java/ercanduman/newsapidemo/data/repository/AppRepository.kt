package ercanduman.newsapidemo.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import ercanduman.newsapidemo.Constants
import ercanduman.newsapidemo.data.db.dao.ArticleDao
import ercanduman.newsapidemo.data.internal.ArticlePagingSource
import ercanduman.newsapidemo.data.internal.safeApiCall
import ercanduman.newsapidemo.data.network.NewsAPI
import ercanduman.newsapidemo.data.network.model.Article
import kotlinx.coroutines.flow.Flow
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
     * Connects NewsAPI, gets data and passes this data to ArticlePagingSource for pagination.
     *
     * And returns flow of PagingData
     */
    suspend fun getBreakingNewsArticles(): Flow<PagingData<Article>> {
        return Pager(
            config = generatePagingConfig(),
            pagingSourceFactory = {
                ArticlePagingSource { position, loadSize ->
                    safeApiCall { api.getArticles(position, loadSize) }
                }
            }
        ).flow
    }

    /**
     * Searches for articles based on query text.
     *
     * Connects NewsAPI, gets data and passes this data to ArticlePagingSource for pagination.
     *
     * And returns flow of PagingData
     */
    fun searchArticles(query: String): Flow<PagingData<Article>> {
        return Pager(
            config = generatePagingConfig(),
            pagingSourceFactory = {
                ArticlePagingSource { position, size ->
                    safeApiCall { api.searchArticles(query, position, size) }
                }
            }).flow
    }

    /**
     * Generates default PagingConfig for and eliminates duplicated codes for re-usages.
     */
    private fun generatePagingConfig() = PagingConfig(
        maxSize = Constants.DEFAULT_MAX_SIZE,
        pageSize = Constants.DEFAULT_PAGE_SIZE,
        enablePlaceholders = false // displaying placeholder for object not loaded yet.
    )


    fun getSavedArticles() = dao.getSavedArticles()
    suspend fun insert(article: Article) = dao.insert(article)
    suspend fun delete(article: Article) = dao.delete(article)
}