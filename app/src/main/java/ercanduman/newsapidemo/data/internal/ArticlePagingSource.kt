package ercanduman.newsapidemo.data.internal

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ercanduman.newsapidemo.data.network.NewsAPI
import ercanduman.newsapidemo.data.network.model.Article
import ercanduman.newsapidemo.util.ApiEvent

/**
 * Class that knows how to fetch data from REST API and turns data into pages.
 *
 * Wont use dagger to inject these parameters, because [searchQuery] is changeable and known only
 * at runtime.
 *
 * @author ercanduman
 * @since  05.03.2021
 */
class ArticlePagingSource(private val api: NewsAPI, private val searchQuery: String) :
    PagingSource<Int, Article>() {

    /**
     * Triggers api request and turns data into pages.
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        // First time of api cal key might be null
        val position = params.key ?: STARTING_PAGE_INDEX

        // call api
        return when (val apiEvent = callApiAndGetApiEvent(position, params)) {
            is ApiEvent.Error -> LoadResult.Error(Throwable(apiEvent.message))
            is ApiEvent.Success -> LoadResult.Page(
                data = apiEvent.data,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (apiEvent.data.isEmpty()) null else position + 1
            )
            else -> LoadResult.Error(Throwable("No Data Found."))
        }
    }

    private suspend fun callApiAndGetApiEvent(position: Int, params: LoadParams<Int>): ApiEvent =
        safeApiCall { api.searchArticles(searchQuery, position, params.loadSize) }


    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return null
    }
}

/**
 * This constant will be used in ArticlePagingSource class but its not belong to that particular class.
 */
private const val STARTING_PAGE_INDEX = 1