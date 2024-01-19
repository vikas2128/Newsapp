package com.example.newsapp.presentation.fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.newsapp.R
import com.example.newsapp.common.UIMessageType
import com.example.newsapp.data.remote.dto.news.Article
import com.example.newsapp.databinding.FragmentNewsSliderBinding
import com.example.newsapp.presentation.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsSliderFragment : Fragment() {
    private val viewModel: NewsViewModel by activityViewModels()
    lateinit var binding: FragmentNewsSliderBinding
    lateinit var layoutManager: LinearLayoutManager
    var initialPos = 0;
    var source = "business"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsSliderBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            initialPos = it.getInt("pos", 1)
            source = it.getString("Source", "business")
        }
        binding.newsRv.apply {
            adapter = NewsSliderAdapter({ article, pos ->
                openNews(article.url)
            }, {
                findNavController().popBackStack()
            })
        }
        binding.arIndicator.attachTo(binding.newsRv, true)

        layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.newsRv.layoutManager = layoutManager
        val snapHelper = PagerSnapHelper()
        //snapHelper.attachToRecyclerView(binding.newsRv)
        observeLoader()
        observeData()
        observeUiMessageEvents()
    }

    private fun openNews(url: String) {
        try {
            val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(myIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                requireContext(), "No application can handle this request."
                        + " Please install a web browser", Toast.LENGTH_LONG
            ).show()
            e.printStackTrace()
        }
    }

    private fun observeUiMessageEvents() {
        viewModel.messageState.observe(viewLifecycleOwner) {
            it?.let { event ->
                event.getContentIfNotHandled().let { uiMessage ->
                    uiMessage?.let { msg ->
                        if (uiMessage.type == UIMessageType.TOAST) {
                            Toast.makeText(
                                requireContext(), msg.message, Toast.LENGTH_SHORT
                            ).show()
                        }
                        if (uiMessage.type == UIMessageType.SNACKBAR) {
                            Snackbar.make(
                                requireView(), msg.message, Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            if (source.equals("business", true)) {
                viewModel.businessNews.collectLatest {
                    setData(it)
                }
            } else if (source.equals("entertainment", true)) {
                viewModel.entertainmentNews.collectLatest {
                    setData(it)
                }
            } else if (source.equals("general", true)) {
                viewModel.scienceNews.collectLatest {
                    setData(it)
                }
            } else if (source.equals("health", true)) {
                viewModel.healthNews.collectLatest {
                    setData(it)
                }
            } else if (source.equals("science", true)) {
                viewModel.scienceNews.collectLatest {
                    setData(it)
                }
            } else if (source.equals("sports", true)) {
                viewModel.sportsNews.collectLatest {
                    setData(it)
                }
            } else if (source.equals("technology", true)) {
                viewModel.technologyNews.collectLatest {
                    setData(it)
                }
            }
        }
    }

    fun setData(article: PagingData<Article>) {
        (binding.newsRv.adapter as NewsSliderAdapter).submitData(
            viewLifecycleOwner.lifecycle,
            article
        )
        binding.newsRv.scrollToPosition(initialPos)
    }

    private fun observeLoader() {
        viewModel.loading.observe(viewLifecycleOwner) {
            it?.let { loading ->
                if (loading) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }
}