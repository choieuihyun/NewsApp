package com.campusmap.android.wanted_preonboarding_android.news

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.campusmap.android.wanted_preonboarding_android.R

import com.campusmap.android.wanted_preonboarding_android.TopNewsViewModel.TopNewsViewModel
import com.campusmap.android.wanted_preonboarding_android.adapter.CategoryItemAdapter
import com.campusmap.android.wanted_preonboarding_android.databinding.CategoriesItemBinding
import com.campusmap.android.wanted_preonboarding_android.databinding.CategoriesItemListBinding

import java.io.Serializable

class CategoriesItem : Fragment() {

    private lateinit var binding: CategoriesItemBinding // 구조 같아서 그냥 이거 사용. 근데 이게 맞나?
    private lateinit var cateItemRecyclerView: RecyclerView
    private lateinit var itemId: Serializable

    private val categoryItemAdapter by lazy {
        CategoryItemAdapter() // 구조 같아서 요거 사용함.
    }

    private val categoryItemViewModel by lazy {
        ViewModelProvider(this).get(TopNewsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        itemId = arguments?.getSerializable("itemId")!!

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.categories_item, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cateItemRecyclerView = view.findViewById(R.id.cateItem_recycler_view)
        cateItemRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        cateItemRecyclerView.adapter = categoryItemAdapter
        Log.d("cateitemlist", itemId.toString())


        when (itemId.toString()) {
            "business" -> {
                categoryItemViewModel.getCategoryItemData(requireContext(), "business")
                cateItemRecyclerView.scrollToPosition(0)
            }

            "entertainment" -> {
                categoryItemViewModel.getCategoryItemData(requireContext(), "entertainment")
            }
            "general" -> {
                categoryItemViewModel.getCategoryItemData(requireContext(), "general")
            }
            "health" -> {
                categoryItemViewModel.getCategoryItemData(requireContext(), "health")
            }
            "science" -> {
                categoryItemViewModel.getCategoryItemData(requireContext(), "science")
            }
            "sports" -> {
                categoryItemViewModel.getCategoryItemData(requireContext(), "sports")
            }
            "technology" -> {
                categoryItemViewModel.getCategoryItemData(requireContext(), "technology")
            }
        }

        categoryItemViewModel.getTopNewsResponseLiveData().observe(
            viewLifecycleOwner,
            {
                categoryItem ->
                    updateUI(categoryItem)
            }
        )

    }

    private fun updateUI(categoryItem: List<Article?>) {
        categoryItemAdapter.submitList(categoryItem)

    }

    companion object {
        fun newInstance(itemId: String) : CategoriesItem {
            val args = Bundle().apply {
                putSerializable("itemId", itemId)
            }
            return CategoriesItem().apply {
                arguments = args
            }
        }
    }
}

/*
class CategoriesItem : Fragment() {

    private lateinit var binding: TopnewsBinding // binding부분 다 고침
    private lateinit var cateItemRecyclerView: RecyclerView
    private lateinit var itemId: Serializable

    private val cateItemAdapter by lazy {
        TopNewsAdapter() // 구조 같아서 요거 사용함.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        itemId = arguments?.getSerializable("itemId")!!


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.topnews, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cateItemRecyclerView = view.findViewById(R.id.topNews_recycler_view)
        cateItemRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        cateItemRecyclerView.adapter = cateItemAdapter
        Log.d("cateitemlist", itemId.toString())


        when(itemId.toString()) {
            "business" -> getCategoryData("business")
            "entertainment" -> getCategoryData("entertainment")
            "general" -> getCategoryData("general")
            "health" -> getCategoryData("health")
            "science" -> getCategoryData("science")
            "sports" -> getCategoryData("sports")
            "technology" -> getCategoryData("technology")
        }

    }
    private fun getCategoryData(category: String) {

        val activity : Activity? = activity

        if (activity != null) {
            (activity as MainActivity).supportActionBar?.title = "Category - ${category.replaceFirstChar { it.uppercase() }}"
        }

        val service = RetrofitClient.topNewsService

        service.getTopNewsCategoryData(getString(R.string.api_key), category).enqueue(object :
            Callback<TopNewsResponse> {
            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {
                if(response.isSuccessful) {
                    Log.d("TAG", response.body().toString())
                    val result = response.body()?.articles
                    cateItemAdapter.submitList(result)
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("TAG", t.message.toString())
            }

        })
    }


    companion object {
        fun newInstance(itemId: String) : CategoriesItem {
            val args = Bundle().apply {
                putSerializable("itemId", itemId)
            }
            return CategoriesItem().apply {
                arguments = args
            }
        }
    }
}*/
