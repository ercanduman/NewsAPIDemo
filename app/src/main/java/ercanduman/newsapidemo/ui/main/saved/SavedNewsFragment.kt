package ercanduman.newsapidemo.ui.main.saved

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import dagger.hilt.android.AndroidEntryPoint
import ercanduman.newsapidemo.R
import ercanduman.newsapidemo.data.network.model.Article
import ercanduman.newsapidemo.databinding.FragmentSavedBinding
import ercanduman.newsapidemo.ui.main.adapter.NewsAdapter
import ercanduman.newsapidemo.ui.main.news.NewsFragmentDirections

@AndroidEntryPoint
class SavedNewsFragment : Fragment(R.layout.fragment_saved), NewsAdapter.OnArticleClicked {

    private val viewModel: SavedNewsViewModel by viewModels()
    private lateinit var binding: FragmentSavedBinding

    private val newsAdapter = NewsAdapter(this)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedBinding.bind(view)

        initRecyclerView()
        observerArticles()
    }

    private fun initRecyclerView() = binding.recyclerView.apply {
        adapter = newsAdapter
        setHasFixedSize(true)
        itemAnimator = null
    }

    private fun observerArticles() {
        viewModel.getSavedArticles().observe(viewLifecycleOwner) {
            newsAdapter.submitData(viewLifecycleOwner.lifecycle, PagingData.from(it))
        }
    }

    override fun articleClicked(article: Article) {
        val action = NewsFragmentDirections.globalActionNavigateToDetailsFragment(article)
        findNavController().navigate(action)
    }
}