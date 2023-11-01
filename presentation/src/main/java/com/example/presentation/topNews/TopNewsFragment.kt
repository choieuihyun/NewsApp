package com.example.presentation.topNews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.presentation.R
import com.example.presentation.categories.Categories
import com.example.presentation.databinding.TopnewsFragmentBinding
import com.example.presentation.saved.SavedItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TopNewsFragment() : Fragment(){

    private lateinit var binding: TopnewsFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //binding = DataBindingUtil.inflate(inflater, R.layout.topnews_fragment, container, false)
        binding = TopnewsFragmentBinding.inflate(inflater)

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
            selectedItemId = R.id.top_news // 첫 화면때 TopNews 띄우는거 이걸로 해결, 생각보다 많은게 이미 되어있구나..
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