package com.example.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.presentation.databinding.TopnewsItemListBinding
import com.example.domain.model.ArticleModel
import com.example.presentation.R

class TopNewsAdapter : ListAdapter<ArticleModel, TopNewsAdapter.TopNewsViewHolder>(TopNewsCallback) {


    interface OnItemClickListener {
        fun onItemClick(v: View?, pos: Int)
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        itemClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopNewsViewHolder {
        val binding = TopnewsItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopNewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopNewsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        Glide.with(holder.itemView)
            .load(item.urlToImage)
            .error(R.drawable.no_image)
            .into(holder.binding.imageView)
    }


    inner class TopNewsViewHolder(val binding: TopnewsItemListBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(item: ArticleModel) {
            binding.title.text = item.title
            binding.author.text = item.author
            binding.publishedAt.text = item.publishedAt
        }

        override fun onClick(v: View?) {
            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                if (itemClickListener != null) {
                    itemClickListener!!.onItemClick(v, pos)
                    Log.d("adapter", "clicked")
                }
            }
        }



    }

    companion object {

        private val TopNewsCallback = object : DiffUtil.ItemCallback<ArticleModel>() {
            override fun areItemsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
                return oldItem == newItem
            }


        }

        }

    }

