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
 * Adapter is a class that adapts every item to a list viewHolder and responsible for providing
 * views that represent items in a data set.

 * PagingDataAdapter listens to internal PagingData loading events as pages are loaded, and uses
 * DiffUtil on a background thread to compute differences as updated content in the form of
 * new PagingData objects are received.
 *
 * @author ercanduman
 * @since  27.02.2021
 */
class NewsAdapter(private val onArticleClickListener: OnArticleClickListener) :
    PagingDataAdapter<Article, NewsAdapter.NewsViewHolder>(ARTICLE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding =
            ListItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentArticle: Article? = getItem(position)
        if (currentArticle != null) holder.bindData(currentArticle)
    }

    fun getCurrentItem(position: Int) = getItem(position)

    /**
     * ViewHolder is a subclass which holds references to the views. A ViewHolder describes an item
     * view and metadata about its place within the RecyclerView.
     *
     * "inner" means this class can use outer class' properties. This makes inner class tightly
     * coupled to surrounding class, but it is ok because they belong together and viewHolder will
     * be used only in this particular adapter.
     */
    inner class NewsViewHolder(private val binding: ListItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                /**
                 * Check if click item position is valid.
                 *
                 * RecyclerView.NO_POSITION is a constant for -1.
                 *
                 * If item clicked during deletion or new insertion processes, then it is possible
                 * that clicked item's position might be invalid which is animating during process.
                 */
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val currentArticle = getItem(position)
                    if (currentArticle != null) onArticleClickListener.onArticleClicked(currentArticle)
                }
            }
        }

        /**
         * Binds passed article data to each view available in [binding].
         */
        fun bindData(article: Article) {
            binding.apply {
                tvArticleTitle.text = article.title
                tvArticleSource.text = article.source?.name
                tvArticleDescription.text = article.description
                tvArticlePublishedAt.text = article.publishedAt

                loadImage(article)
            }
        }

        /**
         * Glide loads images based on their url and displays it in layout.
         *
         * centerCrop   : Image cropped and fits to width and height of layout
         * withCrossFade: apply cross fade animation to image loading.
         * error        : Display an image placeholder for not loaded images.
         */
        private fun ListItemArticleBinding.loadImage(article: Article) {
            Glide.with(this.root)
                .load(article.urlToImage)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_error)
                .into(ivArticleImage)
        }
    }


    /**
     *  DiffUtil can calculate differences between versions of the list. DiffUtil takes both
     *  lists as input and computes the difference, which can be passed to RecyclerView to trigger
     *  minimal animations and updates to keep UI performant, and animations meaningful.
     *
     *  This approach requires that each list is represented in memory with "immutable content",
     *  and relies on receiving updates as new instances of lists.
     */
    companion object {
        private val ARTICLE_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                /* URL is unique for each item */
                return oldItem.url == oldItem.url
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }

    /**
     * This is an interface for listening and forwarding item clicks in fragments.
     *
     * Fragments will implement this listener and can handle functionality in [onArticleClicked] method.
     */
    interface OnArticleClickListener {
        fun onArticleClicked(article: Article)
    }
}