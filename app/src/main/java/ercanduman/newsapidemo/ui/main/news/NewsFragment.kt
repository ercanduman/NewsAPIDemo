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
                is ApiEvent.Empty -> { // Do nothing
                }
                is ApiEvent.Error -> {
                    // binding.textHome.text = "Executed with error. ${event.message} "
                }
                is ApiEvent.Loading -> {
                }
                is ApiEvent.Success -> {
                    // binding.textHome.text = "Executed succesfully. ${event.data.size} "
                }
            }
        }
    }

}