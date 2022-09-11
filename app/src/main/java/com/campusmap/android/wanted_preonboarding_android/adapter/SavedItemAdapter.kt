package com.campusmap.android.wanted_preonboarding_android.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.campusmap.android.wanted_preonboarding_android.databinding.SavedItemListBinding

import com.campusmap.android.wanted_preonboarding_android.roomdb.Saved

class SavedItemAdapter : ListAdapter<Saved, SavedItemAdapter.SavedItemViewHolder>(savedItemCallback) {

    interface OnItemClickListener {
        fun onItemClick(v: View?, pos: Int)
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedItemAdapter.SavedItemViewHolder {
        val binding = SavedItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedItemAdapter.SavedItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SavedItemViewHolder(val binding: SavedItemListBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(item : Saved) {
            binding.savedItemList = item
        }

        override fun onClick(v: View?) {
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

        private val savedItemCallback = object : DiffUtil.ItemCallback<Saved>() {
            override fun areItemsTheSame(oldItem: Saved, newItem: Saved): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            } // areItemsTheSame은 객체가 같은지 판단해야 하기 때문에 주소값으로 비교하거나 객체 내 고유의 값으로 비교를

            override fun areContentsTheSame(oldItem: Saved, newItem: Saved): Boolean {
                return oldItem == newItem
            }


        }
    }


}