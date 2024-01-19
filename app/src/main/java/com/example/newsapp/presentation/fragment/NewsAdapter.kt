package com.example.newsapp.presentation.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.data.remote.dto.news.Article
import com.example.newsapp.databinding.ElementNewsBinding


class NewsAdapter(private val onItemClick: (activity: Article, pos: Int) -> Unit) :
    PagingDataAdapter<Article, NewsAdapter.BodyViewHolder>(ARTICLE_COMPARATOR) {

    class BodyViewHolder(
        private val binding: ElementNewsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article, onItemClick: (activity: Article, pos: Int) -> Unit) {

            try {
                Glide.with(binding.root.context).load(article.urlToImage)
                    .into(binding.imageView)
                binding.titleTv.text = article.title
            } catch (_: Exception) {

            }
            binding.root.setOnClickListener {
                onItemClick.invoke(article, absoluteAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BodyViewHolder {
        return BodyViewHolder(
            ElementNewsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: BodyViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, onItemClick)
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