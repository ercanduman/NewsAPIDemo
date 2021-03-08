package ercanduman.newsapidemo.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import ercanduman.newsapidemo.databinding.LayoutLoadStateFoofterBinding

/**
 * This is completely separated adapter which is responsible to display Header & Footer of existing
 * RecyclerView.
 *
 * Has option to concatenate this adapter ([PagingLoadStateAdapter]) with existing one ([NewsAdapter])
 *
 * Get [retryFunction] as functionality that will be triggered during retry button click.
 *
 * @author ercanduman
 * @since  08.03.2021
 */
class PagingLoadStateAdapter(private val retryFunction: () -> Unit) :
    LoadStateAdapter<PagingLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = LayoutLoadStateFoofterBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return LoadStateViewHolder(binding)
    }

    /**
     * With [loadState] parameter, it can be tracked whether new items are being loaded or if
     * there is any error occurred.
     */
    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bindData(loadState)
    }

    /**
     * ViewHolder is a subclass which holds references to the views. A ViewHolder describes an item
     * view and metadata about its place within the RecyclerView.
     *
     * "inner" means this class can use outer class' properties. This makes inner class tightly
     * coupled to surrounding class, but it is ok because they belong together and viewHolder will
     * be used only in this particular adapter.
     */
    inner class LoadStateViewHolder(private val binding: LayoutLoadStateFoofterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener { retryFunction.invoke() }
        }

        fun bindData(loadState: LoadState) {
            binding.apply {
                btnRetry.isVisible = loadState is LoadState.Loading
                btnRetry.isVisible = loadState !is LoadState.Loading
                tvFooterError.isVisible = loadState !is LoadState.Loading
            }
        }
    }
}