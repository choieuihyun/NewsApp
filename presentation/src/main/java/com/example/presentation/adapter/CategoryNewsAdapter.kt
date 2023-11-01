package com.example.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.ArticleModel
import com.example.presentation.R
import com.example.presentation.databinding.CategoriesItemListBinding

class CategoryNewsAdapter : ListAdapter<ArticleModel, CategoryNewsAdapter.CategoryItemViewHolder>(
    TopNewsCallback
) {

    interface OnItemClickListener {
        fun onItemClick(v: View?, pos: Int)
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        itemClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        val binding = CategoriesItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        Glide.with(holder.itemView)
            .load(item.urlToImage)
            .error(R.drawable.no_image)
            .into(holder.binding.categoriesImageView)
    }


    inner class CategoryItemViewHolder(val binding: CategoriesItemListBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(item: ArticleModel) {
            binding.categoriesTitle.text = item.title
            binding.categoriesAuthor.text = item.author
            binding.categoriesPublishedAt.text = item.publishedAt
        }

        override fun onClick(v: View?) {
            Log.d("TopNews","clickedinAdapter")
            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                Log.d("TopNews", pos.toString())
                if (itemClickListener != null) {
                    itemClickListener!!.onItemClick(v, pos)
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