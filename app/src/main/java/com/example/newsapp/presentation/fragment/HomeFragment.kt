package com.example.newsapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentHomeBinding
import com.example.newsapp.presentation.NewsViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: NewsViewModel by activityViewModels()

    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val loginDialogContainer =
            childFragmentManager.findFragmentById(R.id.main_screen_host_nav) as NavHostFragment
        navController = loginDialogContainer.navController

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.tabPosition.observe(viewLifecycleOwner) {
                it?.let {
                    binding.tabs.getTabAt(it)?.let {
                        binding.tabs.selectTab(it)
                    }
                }
            }
        }
        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: Tab?) {
                tab?.let {
                    viewModel._tabPosition.postValue(it.id)

                    when (tab.position) {
                        0 -> {
                            val bundle = bundleOf("Source" to "business")
                            navController.navigate(R.id.nav_news_list, bundle)
                        }

                        1 -> {
                            val bundle = bundleOf("Source" to "entertainment")
                            navController.navigate(R.id.nav_news_list, bundle)
                        }

                        2 -> {
                            val bundle = bundleOf("Source" to "general")
                            navController.navigate(R.id.nav_news_list, bundle)
                        }

                        3 -> {
                            val bundle = bundleOf("Source" to "health")
                            navController.navigate(R.id.nav_news_list, bundle)
                        }

                        4 -> {
                            val bundle = bundleOf("Source" to "science")
                            navController.navigate(R.id.nav_news_list, bundle)
                        }

                        5 -> {
                            val bundle = bundleOf("Source" to "sports")
                            navController.navigate(R.id.nav_news_list, bundle)
                        }

                        6 -> {
                            val bundle = bundleOf("Source" to "technology")
                            navController.navigate(R.id.nav_news_list, bundle)
                        }

                        else -> {

                        }
                    }
                }
            }

            override fun onTabUnselected(tab: Tab?) {

            }

            override fun onTabReselected(tab: Tab?) {

            }
        })
    }
}