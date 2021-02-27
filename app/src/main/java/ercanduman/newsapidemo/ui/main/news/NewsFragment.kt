package ercanduman.newsapidemo.ui.main.news

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import dagger.hilt.android.AndroidEntryPoint
import ercanduman.newsapidemo.R
import ercanduman.newsapidemo.data.network.model.Article
import ercanduman.newsapidemo.databinding.FragmentNewsBinding
import ercanduman.newsapidemo.ui.main.adapter.NewsAdapter
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
    private val newsAdapter = NewsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)

        initRecyclerView()
        handleApiData()
        applyRetryOption()
    }


    private fun initRecyclerView() = binding.recyclerView.apply {
        adapter = newsAdapter
        setHasFixedSize(true)
        itemAnimator = null
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
                    setData(event.data)
                }
            }
        }
    }

    private fun setData(data: List<Article>) {
        showContent(true)
        log("Article list size: ${data.size}")
        newsAdapter.submitData(viewLifecycleOwner.lifecycle, PagingData.from(data))
    }

    private fun applyRetryOption() {
        binding.buttonRetry.setOnClickListener { newsAdapter.retry() }
        // TODO: 2/27/21 apply retry mechanism for -> newsAdapter.withLoadStateHeaderAndFooter()
        newsAdapter.addLoadStateListener { loadState ->
            binding.apply {
                val isRefresh = loadState.source.refresh
                when (isRefresh) {
                    is LoadState.Loading -> progressBar.show()
                    is LoadState.NotLoading -> showContent(true)
                    is LoadState.Error -> showContent(false)
                }

                // Empty state
                if (isRefresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && newsAdapter.itemCount < 1) {
                    showContent(message = getString(R.string.no_data_found))
                } else {
                    showContent(true)
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