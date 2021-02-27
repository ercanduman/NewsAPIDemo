package ercanduman.newsapidemo.ui.main.news

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ercanduman.newsapidemo.R
import ercanduman.newsapidemo.databinding.FragmentNewsBinding
import ercanduman.newsapidemo.util.ApiEvent
import ercanduman.newsapidemo.util.hide
import ercanduman.newsapidemo.util.log
import ercanduman.newsapidemo.util.show
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment(R.layout.fragment_news) {

    private val viewModel: NewsViewModel by viewModels()
    private lateinit var binding: FragmentNewsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)

        handleApiData()
    }

    private fun handleApiData() = viewLifecycleOwner.lifecycleScope.launch {
        viewModel.getApiEvent(DEFAULT_SEARCH_QUERY)

        viewModel.apiEvent.collect { event ->
            when (event) {
                is ApiEvent.Loading -> binding.progressBar.show()
                is ApiEvent.Empty -> {
                    showContent(message = getString(R.string.no_data_found))
                }
                is ApiEvent.Error -> {
                    showContent(message = getString(R.string.execution_failure, event.message))
                }
                is ApiEvent.Success -> {
                    showContent(true)
                    log("Article list size: ${event.data.size}")
                }
            }
        }
    }

    private fun showContent(contentAvailable: Boolean = false, message: String = "") {
        binding.apply {
            progressBar.hide()
            if (contentAvailable) {
                recyclerView.show()
                retryContent.hide()
            } else {
                recyclerView.hide()
                retryContent.show()
                textViewError.text = message
            }
        }
    }

    companion object {
        private const val DEFAULT_SEARCH_QUERY = "android"
    }
}