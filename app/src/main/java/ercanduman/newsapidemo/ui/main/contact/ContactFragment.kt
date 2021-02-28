package ercanduman.newsapidemo.ui.main.contact

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ercanduman.newsapidemo.R
import ercanduman.newsapidemo.databinding.FragmentContactBinding


class ContactFragment : Fragment(R.layout.fragment_contact) {
    private lateinit var binding: FragmentContactBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentContactBinding.bind(view)
        binding.apply {
            textEmail.setOnClickListener { startImplicitIntent(IntentPage.EMAIL) }
            textGithub.setOnClickListener { startImplicitIntent(IntentPage.GITHUB) }
            textLinkedin.setOnClickListener { startImplicitIntent(IntentPage.LINKEDIN) }
        }
    }

    private fun startImplicitIntent(page: IntentPage) {
        when (page) {
            IntentPage.GITHUB -> startWebIntent("https://github.com/ercanduman")
            IntentPage.LINKEDIN -> startWebIntent("https://www.linkedin.com/in/ercanduman/")
            IntentPage.EMAIL -> {
                val mail = "ercanduman30@gmail.com"
                val url = "mailto:$mail?&subject=${getString(R.string.app_name)} Code"
                startWebIntent(url)
            }
        }
    }

    private fun startWebIntent(url: String) {
        Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
            startActivity(this)
        }
    }

    enum class IntentPage {
        EMAIL, GITHUB, LINKEDIN
    }
}