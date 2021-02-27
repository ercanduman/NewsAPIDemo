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
        viewModel.getApiEvent("android")

        viewModel.apiEvent.collect { event ->
            when (event) {
                is ApiEvent.Empty -> {
                    showContent(false, "No data found.")
                }
                is ApiEvent.Error -> {
                    showContent(false, "Executed with error. ${event.message} ")
                }
                is ApiEvent.Loading -> {
                    binding.progressBar.show()
                }
                is ApiEvent.Success -> {
                    showContent(true, "")
                    log("Article list size: ${event.data.size}")
                }
            }
        }
    }

    private fun showContent(contentAvailable: Boolean, errorMessage: String) {
        binding.apply {
            progressBar.hide()
            if (contentAvailable) {
                recyclerView.show()
                retryContent.hide()
            } else {
                recyclerView.hide()
                retryContent.show()
                textViewError.text = errorMessage
            }
        }
    }

}