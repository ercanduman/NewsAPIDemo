package ercanduman.newsapidemo.ui.main.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ercanduman.newsapidemo.data.repository.AppRepository
import javax.inject.Inject

@HiltViewModel
class SavedNewsViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {
    fun getSavedArticles() = repository.getSavedArticles().asLiveData()
}