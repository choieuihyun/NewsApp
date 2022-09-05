package com.campusmap.android.wanted_preonboarding_android.news

import android.content.Context
import android.text.Layout
import android.util.Log
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





class TopNewsAdapter : ListAdapter<Article, TopNewsAdapter.TopNewsViewHolder>(TopNewsCallback) { // no Type 뭐시기 뜨면 ListAdapter Import 미스.

    private var itemClickListener: OnItemClickListener? = null

    //인터페이스 선언
    interface OnItemClickListener {
        //클릭시 동작할 함수
        fun onItemClick(v: View?, pos: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        itemClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopNewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.topnews_item_list, parent, false)
        return TopNewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopNewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // inner 어지간하면 쓰지 말라던데..
    inner class TopNewsViewHolder(view : View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private val newsImage: ImageView = view.findViewById(R.id.imageView)
        private val newsAuthor: TextView = view.findViewById(R.id.author)
        private val newsTitle: TextView = view.findViewById(R.id.title)
        private val newsPublishAt: TextView = view.findViewById(R.id.publishedAt)

        init {
            view.setOnClickListener(this)
        }

        fun bind(item: Article) {
            newsImage.let {
                Glide.with(it).load(item.urlToImage).into(it)
            }
            newsAuthor.text = item.author
            newsTitle.text = item.title
            newsPublishAt.text = item.publishedAt

        }

        override fun onClick(v: View?) {
            Log.d("TopNews","clickedinAdapter")
            //존재하는 포지션인지 확인
            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
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

/*class TopNewsAdapter : ListAdapter<Article, TopNewsAdapter.TopNewsViewHolder>(TopNewsCallback) { // no Type 뭐시기 뜨면 ListAdapter Import 미스.

    private var itemClickListener: OnItemClickListener? = null

    //인터페이스 선언
    interface OnItemClickListener {
        //클릭시 동작할 함수
        fun onItemClick(v: View?, pos: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        itemClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopNewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.topnews_item_list, parent, false)
        return TopNewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopNewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class TopNewsViewHolder(view : View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private val newsImage: ImageView = view.findViewById(R.id.imageView)
        private val newsAuthor: TextView = view.findViewById(R.id.author)
        private val newsTitle: TextView = view.findViewById(R.id.title)
        private val newsPublishAt: TextView = view.findViewById(R.id.publishedAt)

        fun bind(item: Article) {
            newsImage.let {
                Glide.with(it).load(item.urlToImage).into(it)
            }
            newsAuthor.text = item.author
            newsTitle.text = item.title
            newsPublishAt.text = item.publishedAt
        }



        override fun onClick(v: View?) {
            //존재하는 포지션인지 확인
            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                //동작 호출 (onItemClick 함수 호출)
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, pos)
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


}*/


