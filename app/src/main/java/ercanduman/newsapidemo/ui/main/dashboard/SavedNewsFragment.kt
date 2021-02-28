package ercanduman.newsapidemo.ui.main.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ercanduman.newsapidemo.R

class SavedNewsFragment : Fragment() {

    private lateinit var savedNewsViewModel: SavedNewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        savedNewsViewModel =
            ViewModelProvider(this).get(SavedNewsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        savedNewsViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
        return root
    }
}