package com.campusmap.android.wanted_preonboarding_android.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.campusmap.android.wanted_preonboarding_android.R


object BindingAdapter{
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun bindImage(imageView: ImageView, url: String?) {
        Glide.with(imageView.context).load(url).into(imageView)
    }
}




