package com.example.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.presentation.databinding.SavedItemListBinding
import com.example.domain.model.SavedModel
import com.example.presentation.R

class SavedNewsAdapter : ListAdapter<SavedModel, SavedNewsAdapter.SavedItemViewHolder>(savedItemCallback) {

    interface OnItemClickListener {
        fun onItemClick(v: View?, pos: Int)
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedItemViewHolder {
        val binding = SavedItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        Glide.with(holder.itemView)
            .load(item.urlToImage)
            .error(R.drawable.no_image)
            .into(holder.binding.imageView)
    }

    inner class SavedItemViewHolder(val binding: SavedItemListBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(item : SavedModel) {
            binding.title.text = item.title
            binding.author.text = item.author
            binding.publishedAt.text = item.publishedAt
        }

        override fun onClick(v: View?) {
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

        private val savedItemCallback = object : DiffUtil.ItemCallback<SavedModel>() {
            override fun areItemsTheSame(oldItem: SavedModel, newItem: SavedModel): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
            override fun areContentsTheSame(oldItem: SavedModel, newItem: SavedModel): Boolean {
                return oldItem == newItem
            }


        }
    }


}