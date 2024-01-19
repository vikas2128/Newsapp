package com.example.newsapp.presentation.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.data.remote.dto.news.Article
import com.example.newsapp.databinding.ElementNewsBinding
import com.example.newsapp.databinding.ElementNewsSliderBinding


class NewsSliderAdapter(
    private val onItemClick: (activity: Article, pos: Int) -> Unit,
    private val onBackBtnClick: () -> Unit
) :
    PagingDataAdapter<Article, NewsSliderAdapter.BodyViewHolder>(ARTICLE_COMPARATOR) {

    class BodyViewHolder(
        private val binding: ElementNewsSliderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            article: Article,
            onItemClick: (activity: Article, pos: Int) -> Unit,
            onBackBtnClick: () -> Unit
        ) {
            try {
                Glide.with(binding.root.context).load(article.urlToImage)
                    .into(binding.imageView)
                binding.titleTv.text = article.title
                binding.descriptionTv.text = article.description
            } catch (_: Exception) {
            }
            binding.readmore.setOnClickListener {
                onItemClick.invoke(article, absoluteAdapterPosition)
            }
            binding.backBtn.setOnClickListener {
                onBackBtnClick.invoke()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BodyViewHolder {
        return BodyViewHolder(
            ElementNewsSliderBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: BodyViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, onItemClick, onBackBtnClick)
        }
    }


    object ARTICLE_COMPARATOR : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            // Id is unique.
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
}