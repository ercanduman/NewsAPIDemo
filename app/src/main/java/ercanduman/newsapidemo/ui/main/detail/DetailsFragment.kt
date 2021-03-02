package ercanduman.newsapidemo.ui.main.detail

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ercanduman.newsapidemo.R
import ercanduman.newsapidemo.databinding.FragmentDetailsBinding
import ercanduman.newsapidemo.ui.main.news.NewsViewModel
import ercanduman.newsapidemo.util.hide
import ercanduman.newsapidemo.util.show

/**
 * Display passed article in WebView.
 *
 * FAB button in this fragment will be used to bookmark an article that will be saved locally.
 */
@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {
    private val viewModel: NewsViewModel by viewModels()
    private val navigationArgs: DetailsFragmentArgs by navArgs()

    private lateinit var binding: FragmentDetailsBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsBinding.bind(view)

        displayArticle()
        binding.fabSaveArticle.setOnClickListener {
            viewModel.saveArticleClicked(navigationArgs.article)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun displayArticle() {
        binding.webView.apply {
            webViewClient = webViewCustomClient
            loadUrl(navigationArgs.article.url)
            settings.javaScriptEnabled = true
        }
    }

    private val webViewCustomClient = object : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            binding.progressBar.show()
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            binding.progressBar.hide()
        }
    }
}