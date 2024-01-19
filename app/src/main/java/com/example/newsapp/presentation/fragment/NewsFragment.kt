package com.example.newsapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.newsapp.R
import com.example.newsapp.common.ItemDecoration
import com.example.newsapp.common.UIMessageType
import com.example.newsapp.databinding.FragmentCategoryBinding
import com.example.newsapp.presentation.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class NewsFragment : Fragment() {

    private val viewModel: NewsViewModel by activityViewModels()
    var source = "business"
    lateinit var binding: FragmentCategoryBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            source = it.getString("Source", "business")
        }
        binding.newsRv.apply {
            addItemDecoration(
                ItemDecoration(
                    resources.getDimension(R.dimen.card_spacing).toInt()
                )
            )
            adapter = NewsAdapter { article, pos ->
                val loginDialogContainer =
                    requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_home) as NavHostFragment
                val navController = loginDialogContainer.navController

                val bundle = bundleOf("pos" to pos, "Source" to source)
                navController.navigate(R.id.nav_news_slider, bundle)
            }
        }

        observeLoader()
        observeData()
        observeUiMessageEvents()
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
                    (binding.newsRv.adapter as NewsAdapter).submitData(it)
                }
            } else if (source.equals("entertainment", true)) {
                viewModel.entertainmentNews.collectLatest {
                    (binding.newsRv.adapter as NewsAdapter).submitData(it)
                }
            } else if (source.equals("general", true)) {
                viewModel.scienceNews.collectLatest {
                    (binding.newsRv.adapter as NewsAdapter).submitData(it)
                }
            } else if (source.equals("health", true)) {
                viewModel.healthNews.collectLatest {
                    (binding.newsRv.adapter as NewsAdapter).submitData(it)
                }
            } else if (source.equals("science", true)) {
                viewModel.scienceNews.collectLatest {
                    (binding.newsRv.adapter as NewsAdapter).submitData(it)
                }
            } else if (source.equals("sports", true)) {
                viewModel.sportsNews.collectLatest {
                    (binding.newsRv.adapter as NewsAdapter).submitData(it)
                }
            } else if (source.equals("technology", true)) {
                viewModel.technologyNews.collectLatest {
                    (binding.newsRv.adapter as NewsAdapter).submitData(it)
                }
            }
        }
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