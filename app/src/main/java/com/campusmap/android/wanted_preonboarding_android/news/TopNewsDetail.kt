package com.campusmap.android.wanted_preonboarding_android.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.campusmap.android.wanted_preonboarding_android.R
import com.campusmap.android.wanted_preonboarding_android.ViewModel.TopNewsViewModel
import com.campusmap.android.wanted_preonboarding_android.databinding.TopnewsDetailBinding

class TopNewsDetail : Fragment() {

    private lateinit var binding: TopnewsDetailBinding

    private val topNewsDetailViewModel: TopNewsViewModel by lazy {
        ViewModelProvider(requireActivity()).get(TopNewsViewModel::class.java)
    }

/*    private val topNewsDetailViewModel: TopNewsViewModel by lazy {
        ViewModelProvider(this).get(TopNewsViewModel::class.java)
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.topnews_detail, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        topNewsDetailViewModel.getTopNewsItemData(requireContext())

        val pos = topNewsDetailViewModel.topNewsPosition
        Log.d("TopNewsDetailPos", pos.toString())
        topNewsDetailViewModel.getTopNewsResponseLiveData().observe(
            viewLifecycleOwner,
            {
                topNewsDetail -> updateUI(topNewsDetail[pos]!!)
            }
        )


    }

    override fun onDestroy() {
        super.onDestroy()

    }

    private fun updateUI(item : Article) {
        binding.topNewsDetail = item
    }

    companion object {
        fun newInstance() : TopNewsDetail {
            return TopNewsDetail()
        }
    }

}