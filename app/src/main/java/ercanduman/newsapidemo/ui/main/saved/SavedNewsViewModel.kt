package ercanduman.newsapidemo.ui.main.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ercanduman.newsapidemo.data.network.model.Article
import ercanduman.newsapidemo.data.repository.AppRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedNewsViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {
    fun getSavedArticles() = repository.getSavedArticles().asLiveData()
    fun deleteArticle(article: Article) = viewModelScope.launch { repository.delete(article) }
}