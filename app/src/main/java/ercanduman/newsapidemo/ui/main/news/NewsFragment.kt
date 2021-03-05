package ercanduman.newsapidemo.ui.main.news

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import ercanduman.newsapidemo.Constants
import ercanduman.newsapidemo.R
import ercanduman.newsapidemo.data.network.model.Article
import ercanduman.newsapidemo.databinding.FragmentNewsBinding
import ercanduman.newsapidemo.ui.main.adapter.NewsAdapter
import ercanduman.newsapidemo.util.hide
import ercanduman.newsapidemo.util.show
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Displays all breaking news articles in RecyclerView. Also contains functionality for searching
 * articles based on search query typed inside app bar.
 *
 * @author ercanduman
 * @since  27.02.2021
 */
@AndroidEntryPoint
class NewsFragment : Fragment(R.layout.fragment_news), NewsAdapter.OnArticleClicked {

    private val viewModel: NewsViewModel by viewModels()
    private val newsAdapter = NewsAdapter(this)

    /**
     * Need to be careful when viewBinding used in a fragment. Because, view of a fragment can be
     * destroyed while fragment itself stays in memory. So [binding] field should be cleared during
     * onDestroyView called, otherwise unnecessary view references will kept. Which causes memory
     * leak.
     */
    private var _binding: FragmentNewsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding: FragmentNewsBinding = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewsBinding.bind(view)

        initRecyclerView()
        handleApiData()
        applyRetryOption()
        setHasOptionsMenu(true)
    }

    private fun initRecyclerView() = binding.recyclerView.apply {
        adapter = newsAdapter
        setHasFixedSize(true)
        itemAnimator = null
    }

    private fun handleApiData() {
        viewModel.articles.observe(viewLifecycleOwner) {
            showContent(true)
            newsAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun showLoading() {
        binding.progressBar.show()
        binding.swipeToRefresh.isRefreshing = true
    }

    private fun hideLoading() {
        binding.progressBar.hide()
        binding.swipeToRefresh.isRefreshing = false
    }

    private fun applyRetryOption() {
        binding.buttonRetry.setOnClickListener { newsAdapter.retry() }
        // TODO: 2/27/21 apply retry mechanism for -> newsAdapter.withLoadStateHeaderAndFooter()
        newsAdapter.addLoadStateListener { loadState ->
            binding.apply {
                val isRefresh = loadState.source.refresh
                when (isRefresh) {
                    is LoadState.Loading -> showLoading()
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
            if (contentAvailable) {
                recyclerView.show()
                retryContent.hide()
            } else {
                recyclerView.hide()
                retryContent.show()
                textViewError.text = message
            }
            hideLoading()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_news_fragment, menu)

        val searchView: SearchView =
            menu.findItem(R.id.action_search_news).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean = searchForArticles(newText)
        })
    }

    private fun searchForArticles(newText: String?) =
        if (newText != null && newText.length > 3) {
            /**
             * If text is not null and has at least 3 characters, then wait for a little
             * and call API for new search query.
             *
             * This way api will not called for every characters written to search field.
             */
            viewLifecycleOwner.lifecycleScope.launch {
                delay(Constants.SEARCH_TIME_DELAY)
                viewModel.searchArticlesPaging(newText)
            }
            true
        } else false

    override fun articleClicked(article: Article) {
        val action = NewsFragmentDirections.globalActionNavigateToDetailsFragment(article)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}