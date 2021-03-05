package ercanduman.newsapidemo.ui.main.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ercanduman.newsapidemo.data.network.model.Article
import ercanduman.newsapidemo.data.repository.AppRepository
import ercanduman.newsapidemo.util.ApiEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A ViewModel is responsible for preparing and managing the data for an Activity or a Fragment.
 * ViewModel will not be destroyed if its owner is destroyed for a configuration
 * change (e.g. rotation). The new owner instance just re-connects to the existing model.
 *
 * @HiltViewModel means retrieved by default in an Activity or Fragment annotated with @AndroidEntryPoint.
 * The HiltViewModel containing a constructor annotated with Inject will have its dependencies
 * defined in the constructor parameters injected by Dagger's Hilt.
 *
 * NewsViewModel retrieves Breaking news and searched articles from [AppRepository] and hold it for
 * UI components.
 *
 * @author ercanduman
 * @since  25.02.2021
 */
@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {

    private val _articles = MutableLiveData<PagingData<Article>>()
    val articles: LiveData<PagingData<Article>> = _articles

    /**
     * Loads data for query parameter and passed new data to [_articles] mutable live data.
     *
     * cachedIn: The flow is kept active as long as the given scope is active.
     * To avoid memory leaks, make sure to use a scope that is already managed (like a ViewModel
     * scope) or manually cancel it when you don't need paging anymore.
     */
    fun searchArticlesPaging(query: String) = viewModelScope.launch {
        repository.searchArticlesPagination(query)
            .cachedIn(viewModelScope)
            .collect { _articles.value = it }
    }

    /**
     * Sends parameterized article object to AppRepository to save it locally.
     */
    fun saveArticleClicked(article: Article) = viewModelScope.launch {
        repository.insert(article.copy(isSaved = true))
    }

    /**
     * Retrieves Breaking news by communicating to AppRepository
     */
    /*fun getBreakingNewsArticles() = viewModelScope.launch {
        _apiEvent.value = ApiEvent.Loading
        _apiEvent.value = repository.getArticles()
    }*/
}