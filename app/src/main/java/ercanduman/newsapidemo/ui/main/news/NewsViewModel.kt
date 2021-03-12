package ercanduman.newsapidemo.ui.main.news

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ercanduman.newsapidemo.data.network.model.Article
import ercanduman.newsapidemo.data.repository.AppRepository
import ercanduman.newsapidemo.util.log
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
 * SavedStateHandle ia a handle to saved state passed to ViewModel. This is a key-value map that
 * will let you write and retrieve objects to and from the saved state. These values will persist
 * after the process is killed by the system and remain available via the same object.
 *
 * Annotation of Assisted is a Dagger annotation which uses SavedStateViewModelFactory to receive &
 * inject this object in ViewModel's constructor.
 *
 * @author ercanduman
 * @since  25.02.2021
 */
@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: AppRepository,
    state: SavedStateHandle
) : ViewModel() {

    private val _articles = MutableLiveData<PagingData<Article>>()
    val articles: LiveData<PagingData<Article>> = _articles

    val currentQuery: MutableLiveData<String> = state.getLiveData(KEY_CURRENT_QUERY, "")

    init {
        currentQuery.observeForever { newQuery ->
            log("observeForever called for query: $newQuery")
            getAllArticles(newQuery)
        }
    }

    /**
     * Loads data for query parameter and passed new data to [_articles] mutable live data.
     *
     * cachedIn: The flow is kept active as long as the given scope is active.
     * To avoid memory leaks, make sure to use a scope that is already managed (like a ViewModel
     * scope) or manually cancel it when you don't need paging anymore.
     */
    fun searchArticlesPaging(query: String) {
        currentQuery.value = query
    }

    /**
     * Retrieves Breaking news and searched news by communicating to AppRepository
     *
     * cachedIn: The flow is kept active as long as the given scope is active.
     * To avoid memory leaks, make sure to use a scope that is already managed (like a ViewModel
     * scope) or manually cancel it when you don't need paging anymore.
     */
    private fun getAllArticles(newQuery: String) = viewModelScope.launch {
        if (newQuery.isNotEmpty()) {
            repository.searchArticles(newQuery).cachedIn(viewModelScope).collect {
                _articles.value = it
            }
        } else {
            repository.getBreakingNewsArticles().cachedIn(viewModelScope).collect {
                _articles.value = it
            }
        }
    }

    /**
     * Sends parameterized article object to AppRepository to save it locally.
     */
    fun saveArticleClicked(article: Article) = viewModelScope.launch {
        repository.insert(article.copy(isSaved = true))
    }

    companion object {
        private const val KEY_CURRENT_QUERY = "key_current_query"
    }
}