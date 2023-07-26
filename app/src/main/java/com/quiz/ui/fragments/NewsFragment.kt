package com.quizApp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.fragment.app.viewModels
import com.quizApp.R
import com.quizApp.databinding.FragmentNewsBinding
import com.quizApp.ui.adapters.newsAdapter.NewsAdapter
import com.quizApp.viewModels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var adapter: NewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(layoutInflater, container, false)
        adapter = NewsAdapter()
        binding.recycler.adapter = adapter



        viewModel.newsLiveData.observe(viewLifecycleOwner) {
            binding.progress.visibility = if (it.isLoading) View.VISIBLE else View.GONE
            adapter.submitList(it.articles)
        }

        return binding.root
    }


}