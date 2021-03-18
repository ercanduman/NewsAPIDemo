package ercanduman.newsapidemo.ui.main.saved

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ercanduman.newsapidemo.R
import ercanduman.newsapidemo.data.network.model.Article
import ercanduman.newsapidemo.databinding.FragmentSavedBinding
import ercanduman.newsapidemo.ui.main.adapter.NewsAdapter
import ercanduman.newsapidemo.ui.main.news.NewsFragmentDirections
import ercanduman.newsapidemo.util.snackbarAction

@AndroidEntryPoint
class SavedNewsFragment : Fragment(R.layout.fragment_saved), NewsAdapter.OnArticleClickListener {

    private val viewModel: SavedNewsViewModel by viewModels()
    private lateinit var binding: FragmentSavedBinding

    private val newsAdapter = NewsAdapter(this)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedBinding.bind(view)

        initRecyclerView()
        observerArticles()
        applySwipeToDelete()
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

    private fun applySwipeToDelete() {
        val touchHelper =
            object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.bindingAdapterPosition
                    val currentItem = newsAdapter.getCurrentItem(position)
                    currentItem?.let {
                        viewModel.deleteArticle(it)
                        undoDeletingArticle(it)
                    }
                }
            }

        ItemTouchHelper(touchHelper).attachToRecyclerView(binding.recyclerView)
    }

    private fun undoDeletingArticle(article: Article) {
        binding.root.snackbarAction(getString(R.string.article_deleted), getString(R.string.undo)) {
            viewModel.saveArticle(article)
        }
    }

    override fun onArticleClicked(article: Article) {
        val action = NewsFragmentDirections.globalActionNavigateToDetailsFragment(article)
        findNavController().navigate(action)
    }
}