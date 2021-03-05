package ercanduman.newsapidemo.ui.main.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ercanduman.newsapidemo.data.network.model.Article
import ercanduman.newsapidemo.data.repository.AppRepository
import ercanduman.newsapidemo.util.ApiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _apiEvent = MutableStateFlow<ApiEvent>(ApiEvent.Empty)
    val apiEvent: StateFlow<ApiEvent> = _apiEvent

    /**
     * Retrieves Breaking news by communicating to AppRepository
     */
    fun getBreakingNewsArticles() = viewModelScope.launch {
        _apiEvent.value = ApiEvent.Loading
        _apiEvent.value = repository.getArticles(DEFAULT_PAGE)
    }

    /**
     * Gets data for [searchQuery] by calling AppRepository.
     */
    fun searchForArticles(searchQuery: String) = viewModelScope.launch {
        _apiEvent.value = ApiEvent.Loading
        _apiEvent.value = repository.searchArticles(searchQuery, DEFAULT_PAGE)
    }

    /**
     * Sends parameterized article object to AppRepository to save it locally.
     */
    fun saveArticleClicked(article: Article) = viewModelScope.launch {
        repository.insert(article.copy(isSaved = true))
    }

    companion object {
        private const val DEFAULT_PAGE = 1
    }
}