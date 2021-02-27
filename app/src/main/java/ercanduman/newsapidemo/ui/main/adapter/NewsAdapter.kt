package ercanduman.newsapidemo.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ercanduman.newsapidemo.R
import ercanduman.newsapidemo.data.network.model.Article
import ercanduman.newsapidemo.databinding.ListItemArticleBinding

/**
 * Adapts every item to list item.
 *
 * @author ercan
 * @since  2/27/21
 */
class NewsAdapter : PagingDataAdapter<Article, NewsAdapter.NewsViewHolder>(DIFF_UTIL_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding =
            ListItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentArticle: Article? = getItem(position)
        if (currentArticle != null) holder.bindData(currentArticle)
    }

    class NewsViewHolder(private val binding: ListItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(article: Article) {
            binding.apply {
                tvArticleTitle.text = article.title
                tvArticleSource.text = article.source.name
                tvArticleDescription.text = article.description
                tvArticlePublishedAt.text = article.publishedAt

                Glide.with(binding.root)
                    .load(article.urlToImage)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(ivArticleImage)
            }
        }
    }


    /**
     * DiffUtil callback to compare two Article object and apply changes to RecyclerView.
     */
    companion object {
        private val DIFF_UTIL_CALLBACK = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == oldItem.url
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }
}