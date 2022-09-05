package com.campusmap.android.wanted_preonboarding_android.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.campusmap.android.wanted_preonboarding_android.R

class CateItemAdapter : ListAdapter<Article, CateItemAdapter.CateItemViewHolder>(CateItemCallback) { // no Type 뭐시기 뜨면 ListAdapter Import 미스.


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CateItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.categories_item_list, parent, false)
        return CateItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CateItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class CateItemViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        private val newsImage: ImageView = view.findViewById(R.id.categories_imageView)
        private val newsAuthor: TextView = view.findViewById(R.id.categories_author)
        private val newsTitle: TextView = view.findViewById(R.id.categories_title)
        private val newsPublishAt: TextView = view.findViewById(R.id.categories_publishedAt)

        fun bind(item: Article) {
            newsImage.let {
                Glide.with(it).load(item.urlToImage).into(it)
            }
            newsAuthor.text = item.author
            newsTitle.text = item.title
            newsPublishAt.text = item.publishedAt
        }

    }

    companion object {

        private val CateItemCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }

        }

    }
}