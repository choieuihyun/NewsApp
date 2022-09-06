package com.campusmap.android.wanted_preonboarding_android.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.campusmap.android.wanted_preonboarding_android.databinding.CategoriesItemBinding
import com.campusmap.android.wanted_preonboarding_android.databinding.CategoriesItemListBinding
import com.campusmap.android.wanted_preonboarding_android.databinding.TopnewsItemListBinding
import com.campusmap.android.wanted_preonboarding_android.news.Article

class CategoryItemAdapter : ListAdapter<Article, CategoryItemAdapter.CategoryItemViewHolder>(TopNewsCallback) { // no Type 뭐시기 뜨면 ListAdapter Import 미스.

    private var itemClickListener: OnItemClickListener? = null

    //인터페이스 선언
    interface OnItemClickListener {
        //클릭시 동작할 함수
        fun onItemClick(v: View?, pos: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        itemClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        val binding = CategoriesItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    // inner 어지간하면 쓰지 말라던데..
    inner class CategoryItemViewHolder(val binding: CategoriesItemListBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(item: Article) {
            binding.categoriesItemList = item
//            binding.imageView.let {
//                Glide.with(it).load(binding.topNewsItemList?.urlToImage).into(it)
//            }
        }

        override fun onClick(v: View?) {
            Log.d("TopNews","clickedinAdapter")
            //존재하는 포지션인지 확인
            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                Log.d("TopNews", pos.toString())
                //동작 호출 (onItemClick 함수 호출)
                if (itemClickListener != null) {
                    itemClickListener!!.onItemClick(v, pos)
                }
            }
        }




    }

    companion object {

        private val TopNewsCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }

        }



    }

}