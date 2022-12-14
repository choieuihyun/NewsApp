package com.campusmap.android.wanted_preonboarding_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.campusmap.android.wanted_preonboarding_android.databinding.TopnewsFragmentBinding
import com.campusmap.android.wanted_preonboarding_android.category.Categories
import com.campusmap.android.wanted_preonboarding_android.saved.SavedItem
import com.campusmap.android.wanted_preonboarding_android.news.TopNews
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TopNewsFragment : Fragment() {

    private lateinit var binding: TopnewsFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.topnews_fragment, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onResume() {
        super.onResume()


        binding.bn.run {
            setOnItemSelectedListener { item ->
                when(item.itemId) {
                    R.id.top_news -> {
                        CoroutineScope(Dispatchers.Main).launch {
                            createFragment(TopNews.newInstance())
                        }
                    }
                    R.id.categories -> {
                        CoroutineScope(Dispatchers.Main).launch {
                            createFragment(Categories.newInstance())
                        }
                    }
                    R.id.saved -> {
                        CoroutineScope(Dispatchers.Main).launch {
                            createFragment(SavedItem.newInstance())
                        }
                    }
                }
                true
            }
            selectedItemId = R.id.top_news // ??? ????????? TopNews ???????????? ????????? ??????, ???????????? ????????? ?????? ???????????????..
        }

        binding.bn.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.top_news -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        createFragment(TopNews.newInstance())
                    }
                }
                R.id.categories -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        createFragment(Categories.newInstance())
                    }
                }
                R.id.saved -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        createFragment(SavedItem.newInstance())
                    }
                }
            }
        }
    }

    private fun createFragment(view : Fragment) {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.topNews_container, view)
            .commit()
    }

    companion object {
        fun newInstance() : TopNewsFragment {
            return TopNewsFragment()
        }
    }

}