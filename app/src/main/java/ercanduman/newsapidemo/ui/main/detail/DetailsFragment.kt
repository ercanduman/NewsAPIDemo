package ercanduman.newsapidemo.ui.main.detail

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import ercanduman.newsapidemo.R
import ercanduman.newsapidemo.databinding.FragmentDetailsBinding
import ercanduman.newsapidemo.ui.main.news.NewsViewModel

/**
 * Display passed article in WebView.
 *
 * FAB button in this fragment will be used to bookmark an article that will be saved locally.
 */
class DetailsFragment : Fragment(R.layout.fragment_details) {
    private val viewModel: NewsViewModel by viewModels()
    private val navigationArgs: DetailsFragmentArgs by navArgs()

    private lateinit var binding: FragmentDetailsBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsBinding.bind(view)

        val article = navigationArgs.article

        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }
    }
}