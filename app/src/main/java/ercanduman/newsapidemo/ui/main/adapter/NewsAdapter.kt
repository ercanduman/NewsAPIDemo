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
class NewsAdapter(private val onArticleClicked: OnArticleClicked) :
    PagingDataAdapter<Article, NewsAdapter.NewsViewHolder>(DIFF_UTIL_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding =
            ListItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentArticle: Article? = getItem(position)
        if (currentArticle != null) holder.bindData(currentArticle)
    }

    inner class NewsViewHolder(private val binding: ListItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val adapterPosition = bindingAdapterPosition
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val currentArticle = getItem(adapterPosition)
                    if (currentArticle != null) onArticleClicked.articleClicked(currentArticle)
                }
            }
        }

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
     *  DiffUtil can calculate the difference between versions of the list. DiffUtil takes both
     *  lists as input and computes the difference, which can be passed to RecyclerView to trigger
     *  minimal animations and updates to keep UI performant, and animations meaningful.
     *  This approach requires that each list is represented in memory with "immutable content",
     *  and relies on receiving updates as new instances of lists.
     */
    companion object {
        private val DIFF_UTIL_CALLBACK = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                /* URL is unique for each item */
                return oldItem.url == oldItem.url
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnArticleClicked {
        fun articleClicked(article: Article)
    }
}